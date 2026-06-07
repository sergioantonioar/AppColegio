package com.example.appcolegio.retrofit.dao

import com.example.appcolegio.retrofit.entidades.Menu
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServiceMenu {

    @GET("/menu/lista")
    suspend fun listarMenus(): List<Menu>

    @POST("/menu/registrar")
    suspend fun registrarMenus(@Body bean: Menu)


}