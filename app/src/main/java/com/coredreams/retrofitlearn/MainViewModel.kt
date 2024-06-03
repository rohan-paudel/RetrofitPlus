package com.coredreams.retrofitlearn

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import retrofit2.Retrofit
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()


    val emailData = ObservableField("")
//    val passwordObservable = ObservableField("")

//    val emailData = MutableLiveData<String>("")


    init {
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://jsonplaceholder.typicode.com/todos/").build()
        Log.e("Rhee", "reresesrser")

        val service = retrofit.create(com.rohanpaudel.retrofitplus.ApiService::class.java)
        val apiRepository = com.rohanpaudel.retrofitplus.ApiRepository(service)
        viewModelScope.launch {
            apiRepository.get<Todos>("1").onSuccess {
                Log.e("Rohan Paudel", it.toString())
                _uiState.value = _uiState.value.copy(title = it.title)
            }.onError {
                Log.e("Rohan Paudel", it.toString())
            }
        }

    }

    fun hit() {
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(getOkHttpClient())
            .baseUrl("https://jsonplaceholder.typicode.com/todos/").build()

        val service = retrofit.create(com.rohanpaudel.retrofitplus.ApiService::class.java)
        val apiRepository = com.rohanpaudel.retrofitplus.ApiRepository(service)
        viewModelScope.launch {
            apiRepository.get<Todos>(uiState.value.id, mapOf("rohan" to "hello")).onSuccess {
                _uiState.value = _uiState.value.copy(title = it.title)
            }.onError {
                Log.e("Rohan Paudel", it.toString())
            }
        }
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
            }.readTimeout(15, TimeUnit.SECONDS).build()

    }

    fun updateEmail() {
        Log.e("Rhee", "reresesrser")
        _uiState.value = _uiState.value.copy(id = emailData.get()!!)
        hit()
    }
}

