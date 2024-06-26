package com.example.ecoveggie.Api

import android.util.Base64
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://sowlab.pw/assignment/user"
    private val AUTH = "Basic "+ Base64.encodeToString("Sriansh:123456".toByteArray(), Base64.NO_WRAP)

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original  = chain.request()

            val requestBuilder = original.newBuilder()
                .addHeader("Authorization", AUTH)
                .method(original.method(), original.body())

            val request = requestBuilder.build()
                          chain.proceed(request)

        }.build()

    val instance: Api by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(Api::class.java)
    }
}