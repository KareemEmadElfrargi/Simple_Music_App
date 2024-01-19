package com.example.simplemusicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.simplemusicapp.Remote.ApiInterface
import com.example.simplemusicapp.pojo.Mydata
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData("eminem")

        retrofitData.enqueue(object : Callback<Mydata?> {
            override fun onResponse(call: Call<Mydata?>, response: Response<Mydata?>) {
                val dataList = response.body()?.data
            }

            override fun onFailure(call: Call<Mydata?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}