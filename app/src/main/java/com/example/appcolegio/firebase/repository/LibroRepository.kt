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

    suspend fun buscarLibro(isbn: String): Result<Libro?> {
        try {
            val documentos = db.collection("libros")
                .document(isbn)
                .get()
                .await()
            val lista = documentos.toObject(Libro::class.java)
            return Result.success(lista)
        }
        catch (e: Exception) {
            return Result.failure(e)
        }
    }

    suspend fun actualizarLibro(bean: Libro): Result<Unit> {
        try {
            db.collection("libros")
                .document(bean.isbn)
                .set(bean)
                .await()
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    suspend fun eliminarLibro(isbn: String): Result<Unit> {
        try {
            db.collection("libros")
                .document(isbn)
                .delete()
                .await()
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

}