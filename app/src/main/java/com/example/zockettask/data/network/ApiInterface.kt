package com.example.zockettask.data.network

import com.google.gson.JsonElement
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {

    @POST("fb_page_details")
    suspend fun uploadFacebookDetails(
        @Body data: JsonElement
    ): Response<FacebookLoginModel>



    companion object {
        private const val BASE_URL = "http://65.2.9.217:5000/"

        fun create(): ApiInterface {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

            val client = OkHttpClient.Builder().addInterceptor(logger).build()

            return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ApiInterface::class.java)
        }
    }

}