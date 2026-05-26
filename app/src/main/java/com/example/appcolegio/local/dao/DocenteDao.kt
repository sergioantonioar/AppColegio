package com.example.appcolegio.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.appcolegio.local.entidades.Docente

@Dao
interface DocenteDao {
    @Query("select *from docente")
    suspend fun listar(): List<Docente> //suspend indica que es asincrono
    @Insert
    suspend fun registrar(bean: Docente)
}