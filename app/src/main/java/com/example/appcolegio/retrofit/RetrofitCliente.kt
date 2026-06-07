package com.example.appcolegio.retrofit

import com.example.appcolegio.retrofit.dao.ApiServiceMenu
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitCliente {

    //variable (propiedad)
    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl("https://sistemas2026.onrender.com")
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build()
    }

    //variables
    val menuApi: ApiServiceMenu by lazy {
        retrofit.create(ApiServiceMenu::class.java)
    }

}