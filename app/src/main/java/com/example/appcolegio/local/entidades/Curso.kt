package com.example.appcolegio.local.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Curso(
    @PrimaryKey(autoGenerate = true)
    val codigo: Int =0,
    val nombre: String,
    val ciclo: String,
    val credito: String,
    val carrera: String
) {

}