package com.example.appcolegio.retrofit.dao

import com.example.appcolegio.retrofit.entidades.Menu
import com.example.appcolegio.retrofit.entidades.MenuResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServiceMenu {

    @GET("/men/lista")
    suspend fun listarMenus(): MenuResponse<List<Menu>>

    @GET("/men/buscar/{cod}")
    suspend fun buscarPorCodigo(@Path("cod") codi: Int): MenuResponse<Menu>

    @POST("/men/registrar")
    suspend fun registrarMenus(@Body bean: Menu): MenuResponse<Menu>

    @PUT("/men/actualizar")
    suspend fun actualizarMenus(@Body bean: Menu): MenuResponse<Menu>

    @DELETE("/men/eliminar/{codigo}")
    suspend fun eliminarPorCodigo(@Path("codigo") cod: Int): Unit

}