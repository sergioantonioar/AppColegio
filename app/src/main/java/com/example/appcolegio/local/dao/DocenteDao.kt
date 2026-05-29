package com.example.appcolegio.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.appcolegio.local.entidades.Docente

@Dao
interface DocenteDao {
    @Query("select *from docente")
    suspend fun listar(): List<Docente> //suspend indica que es asincrono

    @Insert
    suspend fun registrar(bean: Docente)

    @Query("select * from docente where codigo=:cod")
    suspend fun buscarPorCodigo(cod: Int): Docente? //? indica optional

    @Update
    suspend fun actualizar(bean: Docente)

    @Delete
    suspend fun eliminar(bean: Docente)
}