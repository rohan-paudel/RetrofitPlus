package com.rohanpaudel.retrofitplus

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private var retrofit: Retrofit? = null

    fun getAPIClient(baseUrl: String): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            ).client(getOkHttpClient()).build()
        }
        return retrofit!!
    }



    private fun getOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor).addNetworkInterceptor { chain ->
                val request = chain.request().newBuilder()
                request.method(chain.request().method, chain.request().body)
                request.build()
                chain.proceed(request.build())
            }.readTimeout(30, TimeUnit.SECONDS).build()

    }


    // No Log
    private fun getOkHttpClientNoLog(): OkHttpClient {
        return OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
            .addNetworkInterceptor {
                val request = it.request().newBuilder()
                request.addHeader("Content-Type", "application/json")
                request.addHeader("sim", "65dddc816e332c5c5f836eac")
                it.proceed(request.build())
            }
            .readTimeout(45, TimeUnit.SECONDS).build()
    }

}