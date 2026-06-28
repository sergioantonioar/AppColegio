package com.example.appcolegio.retrofit.entidades

data class MenuResponse<T>(
    val success: Boolean,
    val mensaje: String,
    val data: T
)