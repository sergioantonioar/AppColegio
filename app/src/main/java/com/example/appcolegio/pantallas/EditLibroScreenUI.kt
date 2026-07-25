package com.example.appcolegio.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appcolegio.firebase.entidades.Libro
import com.example.appcolegio.firebase.viewmodel.LibroViewModel
import com.example.appcolegio.local.AppDatabase
import com.example.appcolegio.local.entidades.Curso
import com.example.appcolegio.local.entidades.Docente
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarLibro(onBack:()->Unit, isbn:String){


    val scope = rememberCoroutineScope()

    var isbm by remember { mutableStateOf("") }
    var titulo by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }


    //obj de LibroViewModel
    val viewLib: LibroViewModel = viewModel()

    //recuperar variable "mensaje" de tipo stateflow
    val men by viewLib.mensaje.collectAsState()

    //recuperar variable "libro" de tipo stateflow
    val lib by viewLib.libro.collectAsState()

    //1era carga
    LaunchedEffect(true) {
        viewLib.findByIsbn(isbn)
    }

    //cuando actualiza lib
    LaunchedEffect(lib) {
        lib?.let {
            isbm = it.isbn
            titulo = it.titulo
            precio = it.precio.toString()
            stock = it.stock.toString()
        }
    }
    //cuando modifica valor mensaje
    LaunchedEffect(men) {
        //validar que no sea null
        men?.let {
            snackbarHostState.showSnackbar(it)
            viewLib.limpiarMensaje()
        }
    }


    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = { Text("Actualizar Libro") },
                navigationIcon = {
                    IconButton(
                        onClick = {onBack()}
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ){ espacio->
        Column(
            modifier = Modifier
                .padding(espacio)
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = isbm,
                onValueChange = {isbm=it},
                label = {Text("Ingresar isbm")},
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = titulo,
                onValueChange = {titulo=it},
                label = {Text("Ingresar titulo")},
                modifier = Modifier
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = precio,
                onValueChange = {precio=it},
                label = {Text("Ingresar precio")},
                modifier = Modifier
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = stock,
                onValueChange = {stock=it},
                label = {Text("Ingresar stock")},
                modifier = Modifier
                    .fillMaxWidth()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ){
                Button(
                    onClick = {
                       viewLib.update(Libro(isbn,titulo,precio.toDouble(),stock.toInt()))
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(5.dp)
                ){
                    Text("Modificar")
                }
            }

        }

    }


}