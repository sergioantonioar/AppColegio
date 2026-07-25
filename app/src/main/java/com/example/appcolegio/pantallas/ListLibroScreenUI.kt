package com.example.appcolegio.pantallas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.appcolegio.local.AppDatabase
import com.example.appcolegio.local.entidades.Docente
import kotlinx.coroutines.launch

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.appcolegio.firebase.entidades.Libro
import com.example.appcolegio.firebase.repository.LibroRepository
import com.example.appcolegio.firebase.viewmodel.LibroViewModel
import com.example.appcolegio.retrofit.entidades.Menu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun pantallaLibros(addLibro:()-> Unit, EditarLibro:(isbn: String)-> Unit) {


    val scope = rememberCoroutineScope()


    var mostrarDialogo by remember {
        mutableStateOf(false)
    }
    var libroEliminar by remember {
        mutableStateOf<Libro?>(null)
    }
    var dismissActual by remember {
        mutableStateOf<SwipeToDismissBoxState?>(null)
    }
    var textoBusqueda by remember {
        mutableStateOf("")
    }

    val viewLib: LibroViewModel = viewModel()
    val lista by viewLib.libros.collectAsState()

    LaunchedEffect(true) {
       viewLib.findAll()
    }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text("Libro")
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White
                )
            )
        },

        bottomBar = {

            BottomAppBar {

                Text(
                    "Inicio",
                    modifier = Modifier
                        .padding(15.dp)
                        .clickable {

                        }
                )

                Text(
                    "Docente",
                    modifier = Modifier.padding(15.dp)
                )

                Text(
                    "Curso",
                    modifier = Modifier
                        .padding(15.dp)
                        .clickable {

                        }
                )
            }
        },

        floatingActionButton = {

            FloatingActionButton(
                onClick = {
                    addLibro()
                }
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
                value = textoBusqueda,
                onValueChange = {
                    textoBusqueda = it

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                        top=10.dp
                    ),
                placeholder = {
                    Text("Buscar menu")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
            )
            LazyColumn(

                modifier = Modifier.fillMaxSize(),

                contentPadding = PaddingValues(15.dp),

                verticalArrangement = Arrangement.spacedBy(10.dp)

            ) {

                items(
                    items = lista,
                    key = { it.isbn }
                ) { bean ->

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

                            libroEliminar = bean
                            mostrarDialogo = true
                            dismissActual = dismissState
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
                                        .padding(20.dp),

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
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                EditarLibro(bean.isbn)
                            }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Text(
                                        "ISBN : ${bean.isbn}",
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text("Título : ${bean.titulo}")
                                    Text("Precio : ${bean.precio}")
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
                        Text("¿Seguro de eliminar el libro?")
                    },

                    confirmButton = {

                        Button(

                            onClick = {

                                scope.launch {
                                    viewLib.deleteByIsbn(libroEliminar!!.isbn)
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

                                    dismissActual?.snapTo(
                                        SwipeToDismissBoxValue.Settled
                                    )

                                    mostrarDialogo = false
                                }
                            }

                        ) {

                            Text("No")
                        }
                    }
                )
            }
        }
    }
}