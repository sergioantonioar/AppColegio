package com.example.appcolegio.retrofit.entidades

data class AlumnoResponse<T>(
    val success: Boolean,
    val mensaje: String,
    val data: T
)
