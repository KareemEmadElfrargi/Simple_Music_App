package com.example.simplemusicapp.pojo


import com.google.gson.annotations.SerializedName

data class Mydata(
    @SerializedName("data")
    val `data`: List<Data>?,
    @SerializedName("next")
    val next: String?,
    @SerializedName("total")
    val total: Int?
)