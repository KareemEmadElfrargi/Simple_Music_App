import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemusicapp.R
import com.example.simplemusicapp.pojo.Data
import com.squareup.picasso.Picasso

class MyAdapter(val context: Context, val dataList: List<Data>) :
    RecyclerView.Adapter<MyAdapter.ViewHolderData>() {

    inner class ViewHolderData(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mediaPlayer = MediaPlayer()
        val musicImage: ImageView = itemView.findViewById(R.id.imageSong)
        val titleOfSong: TextView = itemView.findViewById(R.id.textSongName)
        val play: Button = itemView.findViewById(R.id.btnPlay)
        val stop: Button = itemView.findViewById(R.id.btnStop)
        val seekBar: SeekBar = itemView.findViewById(R.id.seekBar)

        init {
            // Set up SeekBar change listener
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        mediaPlayer.seekTo(progress)
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    // Handle touch start
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    // Handle touch end
                }
            })

            // Set up completion listener
            mediaPlayer.setOnCompletionListener {
                // Media playback completed, update button state
                updateButtonState(this@ViewHolderData)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderData {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.each_item, parent, false)
        return ViewHolderData(itemView)
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: ViewHolderData, currentPosition: Int) {
        val currentData = dataList[currentPosition]

        holder.titleOfSong.text = currentData.title.toString()
        Picasso.get().load(currentData.album?.cover).into(holder.musicImage)

        updateButtonState(holder)

        holder.play.setOnClickListener {
            if (!holder.mediaPlayer.isPlaying) {
                if (holder.mediaPlayer.currentPosition == 0 || holder.mediaPlayer.currentPosition == holder.mediaPlayer.duration) {
                    // Start playing from the beginning or after completion
                    holder.mediaPlayer.reset()
                    holder.mediaPlayer.setDataSource(context, currentData.preview?.toUri()!!)
                    holder.mediaPlayer.prepare()
                    holder.seekBar.max = holder.mediaPlayer.duration
                }
                holder.mediaPlayer.start()
                startSeekBarUpdate(holder)
            } else {
                holder.mediaPlayer.pause()
                stopSeekBarUpdate(holder)
            }

            updateButtonState(holder)
        }

        holder.stop.setOnClickListener {
            if (holder.mediaPlayer.isPlaying) {
                holder.mediaPlayer.stop()
                holder.mediaPlayer.reset()
                stopSeekBarUpdate(holder)
                updateButtonState(holder)
            }
        }
    }

    private fun startSeekBarUpdate(holder: ViewHolderData) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                holder.seekBar.progress = holder.mediaPlayer.currentPosition
                handler.postDelayed(this, 1000) // Update every second
            }
        }, 1000)
    }

    private fun stopSeekBarUpdate(holder: ViewHolderData) {
        holder.seekBar.progress = 0
    }

    private fun updateButtonState(holder: ViewHolderData) {
        if (holder.mediaPlayer.isPlaying) {
            holder.play.text = "Pause"
            holder.stop.isEnabled = true
        } else {
            holder.play.text = "Play"
            holder.stop.isEnabled = false
        }
    }

    override fun onViewRecycled(holder: ViewHolderData) {
        super.onViewRecycled(holder)
        // Release MediaPlayer resources when ViewHolder is recycled
        holder.mediaPlayer.release()
    }
}
