package com.rohanpaudel.retrofitplus


import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ApiRepository(val apiService: ApiService) {

    suspend inline fun <reified T> get(
        url: String, params: Map<String, String> = emptyMap()
    ): ApiResponse<T> {
        try {
            val response = apiService.get(url, params)
            if (response.isSuccessful) {
                val todos = response.body()
                todos?.let {
                    val gson = Gson()
                    val type = object : TypeToken<T>() {}.type
                    val data: T = gson.fromJson(it, type)
                    return ApiResponse.Success(data)
                }
            }
            return ApiResponse.Error(Exception(response.message()))
        } catch (e: Exception) {
            return ApiResponse.Error(Exception(e.message))
        }
    }

}