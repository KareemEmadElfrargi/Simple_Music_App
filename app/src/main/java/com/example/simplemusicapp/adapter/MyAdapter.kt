package com.example.simplemusicapp.adapter

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemusicapp.R
import com.example.simplemusicapp.pojo.Data
import com.example.simplemusicapp.pojo.Mydata
import com.squareup.picasso.Picasso

class MyAdapter(val context:Context,val dataList: List<Data>):RecyclerView.Adapter<MyAdapter.ViewHolderData>() {
    inner class ViewHolderData (itemView:View) : RecyclerView.ViewHolder(itemView){
        // create the view
         val musicImage :ImageView
         val titleOfSong:TextView
         val play:Button
         val stop:Button
        init {
            musicImage = itemView.findViewById(R.id.imageSong)
            titleOfSong = itemView.findViewById(R.id.textSongName)
            play = itemView.findViewById(R.id.btnPlay)
            stop = itemView.findViewById(R.id.btnStop)

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderData {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.each_item,parent,false)
        return ViewHolderData(itemView)
    }

    override fun getItemCount() = dataList.size
    override fun onBindViewHolder(holder: ViewHolderData, currentPostion: Int) {
        val currentData = dataList[currentPostion]
        val mediaPlayer = MediaPlayer.create(context,currentData.preview?.toUri())
        holder.titleOfSong.text = currentData.title.toString()
        Picasso.get().load(currentData.album?.cover).into(holder.musicImage)
        holder.play.setOnClickListener {
            mediaPlayer.start()
        }

        holder.stop.setOnClickListener {
            mediaPlayer.stop()
        }
    }

}