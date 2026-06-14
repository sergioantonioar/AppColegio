package com.example.appcolegio.pantallas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import com.example.appcolegio.local.AppDatabase
import com.example.appcolegio.local.entidades.Docente
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.appcolegio.navegacion.MenuInferior
import com.example.appcolegio.retrofit.RetrofitCliente
import com.example.appcolegio.retrofit.entidades.Alumno
import com.example.appcolegio.retrofit.entidades.Menu
import kotlinx.coroutines.launch

//01:12:23 BUSQUEDA

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaAlumno(
    addAlumno: () -> Unit
) {

    val scope = rememberCoroutineScope()

    var lista by remember {
        mutableStateOf(listOf<Alumno>())
    }

    //mostrar alerta
    var mostrarDialogo by remember { mutableStateOf(false) }

    var dismissActual by remember { mutableStateOf<SwipeToDismissBoxState?>(null) }

    var alumnoActual by remember { mutableStateOf<Alumno?>(null) }

    var valorBuscado by remember { mutableStateOf("") }


    //para se ejecute una sola vez por el composable y no haga cuello de botella
    LaunchedEffect(true) {
        scope.launch {
            lista = RetrofitCliente.alumnoApi.listarAlumnos()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Alumno") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomAppBar {
                MenuInferior(
                    onDocente = {},
                    onCurso = {  }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { addAlumno() },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) { espacio ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(espacio)

        ) {
            OutlinedTextField(
                value = valorBuscado,
                onValueChange = {
                    valorBuscado = it
                    scope.launch {

                    }

                },
                placeholder = { Text("Buscar Alumno") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                }
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(15.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(lista, key = { it.codigo }) { bean ->

                    val dismissState = remember {
                        SwipeToDismissBoxState(
                            initialValue = SwipeToDismissBoxValue.Settled,
                            positionalThreshold = { it * 0.25f }
                        )
                    }

                    LaunchedEffect(dismissState.currentValue) {
                        if (
                            dismissState.currentValue == SwipeToDismissBoxValue.EndToStart ||
                            dismissState.currentValue == SwipeToDismissBoxValue.StartToEnd
                        ) {
                            mostrarDialogo = true
                            dismissActual = dismissState
                            alumnoActual = bean

                        }
                    }

                    SwipeToDismissBox(
                        state = dismissState,
                        backgroundContent = {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.Red
                                )
                            ) {

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(30.dp),
                                    contentAlignment = Alignment.CenterEnd
                                ) {

                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null,
                                        tint = Color.White

                                    )

                                }

                            }

                        }

                    ) {

                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            onClick = {  }
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(15.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Column(
                                    modifier = Modifier
                                        .padding(15.dp),
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Text("Código : ${bean.codigo}", fontWeight = FontWeight.Bold)
                                    Text("Nombres : ${bean.nombre}")
                                    Text("Apellidos : ${bean.paterno} ${bean.materno}")

                                }

                                AsyncImage(
                                    model = bean.foto,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(70.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }

                        }


                    }


                }


            }

        }


    }
    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = {
                //mostrarDialogo = false
            },
            title = {
                Text("Confirmación")
            },
            text = {
                Text("¿Seguro de eliminar el docente?")
            },

            confirmButton = {
                Button(
                    onClick = {
                        scope.launch {
                            RetrofitCliente.alumnoApi
                                .eliminarPorCodigo(alumnoActual!!.codigo)
                            lista = RetrofitCliente.alumnoApi
                                .listarAlumnos()
                            mostrarDialogo = false



                        }
                    }

                ) {

                    Text("Sí")

                }

            },

            dismissButton = {

                OutlinedButton(
                    onClick = {
                        scope.launch {
                            mostrarDialogo = false
                            dismissActual?.snapTo(SwipeToDismissBoxValue.Settled)
                        }

                    }

                ) {

                    Text("No")

                }

            }

        )
    }
}
