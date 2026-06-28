package com.example.appcolegio.retrofit.entidades

data class ErrorResponse (
    val success: Boolean,
    val mensaje: String,
    val data: Map<String, String>
)