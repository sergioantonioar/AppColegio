package com.example.appcolegio.firebase.entidades

//inicializar
data class Libro(
    val isbn: String = "",
    val titulo: String = "",
    val precio: Double = 0.0,
    val stock: Int = 0
) {
}