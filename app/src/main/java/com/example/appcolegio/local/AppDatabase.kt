package com.example.appcolegio.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appcolegio.local.dao.DocenteDao
import com.example.appcolegio.local.entidades.Docente

@Database(
    entities = [Docente::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun docenteDao(): DocenteDao
}