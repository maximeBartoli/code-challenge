package com.example.code_challenge.model

import com.squareup.moshi.Json

data class Article(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "summary")
    val description: String,
    @Json(name = "largeThumbnail")
    val image: String,
    @Json(name = "content")
    val content:String
)
