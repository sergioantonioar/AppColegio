package com.example.appcolegio.retrofit.dao

import com.example.appcolegio.retrofit.entidades.Menu
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServiceMenu {

    @GET("/menu/lista")
    suspend fun listarMenus(): List<Menu>

    @GET("/menu/buscar/{cod}")
    suspend fun buscarPorCodigo(@Path("cod") codi: Int): Menu

    @POST("/menu/registrar")
    suspend fun registrarMenus(@Body bean: Menu)

    @PUT("/menu/actualizar")
    suspend fun actualizarMenus(@Body bean: Menu)

    @DELETE("/menu/eliminar/{codigo}")
    suspend fun eliminarPorCodigo(@Path("codigo") cod: Int): Unit

}