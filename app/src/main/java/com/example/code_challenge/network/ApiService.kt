package com.example.code_challenge.network

import com.example.code_challenge.model.Article
import com.example.code_challenge.model.Item
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL=
    "https://api.goodbarber.net/front/get_items/826899/14560013/?local=1"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL).build()

interface ApiService{

    @GET(".")
    fun getAllData(): Call<Item>

}

object Api {
    val retrofitService: ApiService by lazy{retrofit.create(ApiService::class.java)}
}