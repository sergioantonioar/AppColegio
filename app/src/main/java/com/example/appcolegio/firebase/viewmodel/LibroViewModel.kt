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

    fun limpiarMensaje() {
        _mensaje.value = null
    }


}