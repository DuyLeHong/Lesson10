package com.example.lesson10

import com.google.gson.annotations.SerializedName

class Album(
    @field:SerializedName("id") var id: Int,
    @field:SerializedName("title") var title: String,
    @field:SerializedName(
        "url"
    ) var url: String
)