package com.example.appcolegio.retrofit.dao

import com.example.appcolegio.retrofit.entidades.Alumno
import com.example.appcolegio.retrofit.entidades.AlumnoResponse
import com.example.appcolegio.retrofit.entidades.Menu
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServiceAlumno {

    @GET("/alu/lista")
    suspend fun listarAlumnos(): AlumnoResponse<List<Alumno>>

    @GET("/alu/buscar/{cod}")
    suspend fun buscarPorCodigo(@Path("cod") codi: Int): AlumnoResponse<Alumno>

    @POST("/alu/registrar")
    suspend fun registrarAlumnos(@Body bean: Alumno): AlumnoResponse<Alumno>

    @PUT("/alu/actualizar")
    suspend fun actualizarAlumnos(@Body bean: Alumno): AlumnoResponse<Alumno>

    @DELETE("/alu/eliminar/{codigo}")
    suspend fun eliminarPorCodigo(@Path("codigo") cod: Int): Unit
}