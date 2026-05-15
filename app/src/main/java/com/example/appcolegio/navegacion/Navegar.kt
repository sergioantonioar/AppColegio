package com.example.appcolegio.navegacion

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay

data object Home
data class Product(val id: String)

@Composable
fun Navegar() {

    val backStack = remember { mutableStateListOf<Any>(Home) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is Home -> NavEntry(key) {

                    backStack.add(Product("123"))

                }
            }

            is Product -> NavEntry(key) {
            //ContentBlue("Product ${key.id} ")
        }

            else -> NavEntry(Unit) { Text("Unknown route") }
        }
}