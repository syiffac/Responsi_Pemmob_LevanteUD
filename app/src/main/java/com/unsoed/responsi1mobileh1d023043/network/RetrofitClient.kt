package com.unsoed.responsi1mobileh1d023043.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    
    // Base URL untuk Football Data API
    private const val BASE_URL = "https://api.football-data.org/v4/"
    
    // API Token - GANTI DENGAN TOKEN ANDA
    const val API_TOKEN = "7e294122d5744968a101ac718e8bbe62"
    
    // ID untuk Levante UD
    const val LEVANTE_TEAM_ID = 88
    
    // Logging interceptor untuk debugging
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    // OkHttp client dengan timeout dan logging
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
    
    // Retrofit instance
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    // API Service instance
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}