package com.example.appcolegio.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import com.example.appcolegio.clases.Docente

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaDocente(addDocente: () -> Unit) {

    var lista = remember {
        mutableListOf(
            Docente(1, "Ana", "Soto Mora", "Femnenino", 2500.0, 0),
            Docente(2, "Jose", "Ramirez Ariza", "Masculino", 5000.0, 1)
        )
    }

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
            FloatingActionButton(
                onClick = { addDocente() },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) { espacio ->
        LazyColumn(
            modifier = Modifier
                .padding(espacio),
            contentPadding = PaddingValues(15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(lista) { bean ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(15.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text("Codigo: ${bean.codigo}", fontWeight = FontWeight.Bold)
                        Text("Nombres: ${bean.nombres}")
                        Text("Apellidos: ${bean.apellidos}")

                    }
                }
            }


        }

    }
}
