package com.example.appcolegio.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appcolegio.local.dao.CursoDao
import com.example.appcolegio.local.dao.DocenteDao
import com.example.appcolegio.local.entidades.Curso
import com.example.appcolegio.local.entidades.Docente

@Database(
    entities = [Docente::class, Curso::class],
    version = 2
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun docenteDao(): DocenteDao
    abstract fun cursoDao(): CursoDao
}