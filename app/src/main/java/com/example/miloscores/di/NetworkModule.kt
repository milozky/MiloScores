package com.example.miloscores.di

import com.example.miloscores.BuildConfig
import com.example.miloscores.data.api.MatchApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import android.util.Log

val networkModule = module {
    single { createOkHttpClient() }
    single { createRetrofit(get()) }
    single { createApiService(get()) }
}

private fun createOkHttpClient(): OkHttpClient {
    Log.d("NetworkModule", "API Key: ${BuildConfig.FOOTBALL_API_KEY}")
    
    return OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("x-rapidapi-key", BuildConfig.FOOTBALL_API_KEY)
                .addHeader("x-rapidapi-host", "api-football-v1.p.rapidapi.com")
                .build()
            Log.d("NetworkModule", "Request headers: ${request.headers}")
            chain.proceed(request)
        }
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
}

private fun createRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api-football-v1.p.rapidapi.com/v3/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun createApiService(retrofit: Retrofit): MatchApiService {
    return retrofit.create(MatchApiService::class.java)
} 