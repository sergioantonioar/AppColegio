package com.example.appcolegio.firebase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcolegio.firebase.entidades.Libro
import com.example.appcolegio.firebase.repository.LibroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LibroViewModel : ViewModel() {
    private val repo = LibroRepository() //crear obj de repository

    //variable interna, alcance libroviewmodel
    private val _mensaje = MutableStateFlow<String?>(null)

    //variable externa, alcance para la UI
    val mensaje: StateFlow<String?> = _mensaje

    //listar
    private val _libros = MutableStateFlow<List<Libro>>(emptyList())

    val libros: StateFlow<List<Libro>> = _libros

    private val _libro = MutableStateFlow<Libro?>(null)
    val libro: StateFlow<Libro?> = _libro

    fun save(lib: Libro) {
        //couritine por defecto de viewmodel
        viewModelScope.launch {
            repo.registrarLibro(lib)
                .onSuccess {
                    _mensaje.value = "Libro Registrado"
                }
                .onFailure {
                    _mensaje.value = it.message
                }
        }
    }

    fun findAll() {
        viewModelScope.launch {
            repo.listarLibros()
                .onSuccess {
                    _libros.value = it
                }
                .onFailure {
                    _mensaje.value = it.message
                }
        }
    }

    fun findByIsbn(isbn: String) {
        viewModelScope.launch {
            repo.buscarLibro(isbn)
                .onSuccess {
                    _libro.value = it
                }
                .onFailure {
                    _mensaje.value = it.message
                }
        }
    }

    fun update(lib: Libro) {
        viewModelScope.launch {
            repo.actualizarLibro(lib)
                .onSuccess {
                    _mensaje.value = "Libro actualizado"
                }
                .onFailure {
                    _mensaje.value = it.message
                }
        }
    }

    fun deleteByIsbn(isbn: String) {
        viewModelScope.launch {
            repo.eliminarLibro(isbn)
                .onSuccess {
                    _mensaje.value = "Libro eliminado"
                    findAll() //para actualizar lista
                }
                .onFailure {
                    _mensaje.value = it.message
                }
        }
    }

    fun limpiarMensaje() {
        _mensaje.value = null
    }


}