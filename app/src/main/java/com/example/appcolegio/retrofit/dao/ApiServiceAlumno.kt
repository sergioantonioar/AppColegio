package com.example.appcolegio.retrofit.dao

import com.example.appcolegio.retrofit.entidades.Alumno
import com.example.appcolegio.retrofit.entidades.Menu
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServiceAlumno {

    @GET("/alumno/lista")
    suspend fun listarAlumnos(): List<Alumno>

    @GET("/alumno/buscar/{cod}")
    suspend fun buscarPorCodigo(@Path("cod") codi: Int): Alumno

    @POST("/alumno/registrar")
    suspend fun registrarAlumnos(@Body bean: Alumno)

    @PUT("/alumno/actualizar")
    suspend fun actualizarAlumnos(@Body bean: Alumno)

    @DELETE("/alumno/eliminar/{codigo}")
    suspend fun eliminarPorCodigo(@Path("codigo") cod: Int): Unit
}