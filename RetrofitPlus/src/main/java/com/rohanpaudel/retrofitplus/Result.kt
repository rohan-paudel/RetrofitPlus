package com.rohanpaudel.retrofitplus

sealed interface ApiResponse<T> {
    data class Success<T>(val data: T) : ApiResponse<T>
    data class Failure<T>(val data: T) : ApiResponse<T>
    data class Error<T>(val exception: java.lang.Exception) : ApiResponse<T>

    suspend fun onSuccess(block: suspend (T) -> Unit): ApiResponse<T> {
        if (this is Success) block(data)
        return this
    }

    suspend fun onFailure(block: suspend (T) -> Unit): ApiResponse<T> {
        if (this is Failure) block(data)
        return this
    }

    suspend fun onError(block: suspend (java.lang.Exception) -> Unit): ApiResponse<T> {
        if (this is Error) block(exception)
        return this
    }
}