package com.example.simplemusicapp.Remote

import com.example.simplemusicapp.pojo.Mydata
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {

    @Headers("X-RapidAPI-Key: 4f5574945dmsh7197870d5f30131p1a4bebjsnc46bf23d7896",
            "X-RapidAPI-Host: deezerdevs-deezer.p.rapidapi.com")
    @GET("search")
    fun getData(
        @Query ("q") query: String
    ) : Call<Mydata>
}