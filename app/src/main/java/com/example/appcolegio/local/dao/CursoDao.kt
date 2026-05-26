package com.example.appcolegio.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.appcolegio.local.entidades.Curso

@Dao
interface CursoDao {
    @Query("select *from curso")
    suspend fun listar(): List<Curso> //suspend indica que es asincrono
    @Insert
    suspend fun registrar(bean: Curso)
}