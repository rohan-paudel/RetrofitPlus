package com.coredreams.retrofitlearn

import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url


interface ApiService {
    @GET
    suspend fun get(
        @Url url: String,
        @QueryMap params: Map<String, String>
    ): Response<JsonElement>
}