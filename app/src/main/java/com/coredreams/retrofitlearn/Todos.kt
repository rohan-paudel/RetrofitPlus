package com.coredreams.retrofitlearn

data class Todos(
    val completed: Boolean,
    val id: Int,
    val title: String,
    val userId: Int
)