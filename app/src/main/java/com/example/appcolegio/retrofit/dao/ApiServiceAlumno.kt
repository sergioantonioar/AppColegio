package com.example.appcolegio.retrofit.dao

import com.example.appcolegio.retrofit.entidades.Alumno
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServiceAlumno {

    @GET("/alumno/lista")
    suspend fun listarAlumnos(): List<Alumno>

    @POST("/alumno/registrar")
    suspend fun registrarAlumnos(@Body bean: Alumno)

    @DELETE("/alumno/eliminar/{codigo}")
    suspend fun eliminarPorCodigo(@Path("codigo") cod: Int): Unit
}