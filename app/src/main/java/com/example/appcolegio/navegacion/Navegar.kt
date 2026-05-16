package com.example.appcolegio.navegacion

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.example.appcolegio.pantallas.AdicionarDocente
import com.example.appcolegio.pantallas.ListaDocente

data object ListDocente
data object AddDocente

data class Product(val id: String)

@Composable
fun Navegar() {

    val backStack = remember { mutableStateListOf<Any>(ListDocente) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is ListDocente -> NavEntry(key) {
                    ListaDocente(addDocente = {backStack.add(AddDocente)})
                }

                is AddDocente -> NavEntry(key) {
                    AdicionarDocente(onBack = {backStack.removeLastOrNull()})
                }

                else -> NavEntry(Unit) { Text("Unknown route") }
            }
        }
    )
}