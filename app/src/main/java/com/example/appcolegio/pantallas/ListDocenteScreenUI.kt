package com.example.appcolegio.pantallas

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun listaDocente() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Docente") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White
                )

            )
        },
        bottomBar = {
            BottomAppBar {
                Text("Inicio", modifier = Modifier.padding(15.dp))
                Text("Docente", modifier = Modifier.padding(15.dp))

            }
        },
        floatingActionButton = {

        }
    ) { espacio -> {

        }
    }
}
