package com.ruiaaryan.memeshare

import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.converter.moshi.MoshiConverterFactory

class MemeAPIservice {

    companion object {
        private const val BASE_URL = "https://meme-api.herokuapp.com/"

        private val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory
            .create(moshi))
            .build()
    }

    interface MemeAPIService {
        @GET("gimme/{word}")
        fun getPhoto(@Path("word")word:String) : Call<MemeResponse>

    }
    object Memeapi{
        val retrofitService : MemeAPIService by lazy {
            retrofit.create(MemeAPIService::class.java) }
    }
}