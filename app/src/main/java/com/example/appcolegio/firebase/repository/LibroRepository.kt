package com.example.appcolegio.firebase.repository

import com.example.appcolegio.firebase.entidades.Libro
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

//00:54:39
class LibroRepository {
    //acceder a la bd firestore
    private val db = FirebaseFirestore.getInstance()

    suspend fun registrarLibro(bean: Libro): Result<Unit> {
        try {
            //crear collection
            db.collection("libros")
                .document(bean.isbn)
                .set(bean)
                .await() //espera a que el task de set termine
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    suspend fun listarLibros(): Result<List<Libro>> {
        try {
            val documentos = db.collection("libros")
                .get()
                .await()
            val lista = documentos.toObjects(Libro::class.java) //para traer objs libro
            return Result.success(lista)
        }
        catch (e: Exception) {
            return Result.failure(e)
        }
    }

}