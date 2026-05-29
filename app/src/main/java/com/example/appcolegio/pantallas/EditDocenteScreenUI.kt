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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appcolegio.local.AppDatabase
import com.example.appcolegio.local.entidades.Docente
import kotlinx.coroutines.launch


//01:13:24
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarDocente(onBack: () -> Unit, db: AppDatabase, codigo:Int) {

    //obtener el DAO
    val dao = db.docenteDao()

    //crear objeto de una corrutina
    val scope = rememberCoroutineScope()

    //snackbar
    val snackbar=remember { SnackbarHostState() }

    var nombres by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    val sexos = listOf("Masculino", "Femenino", "Otros")
    var expanded by remember { mutableStateOf(false) }
    var nomSexo by remember { mutableStateOf("") }
    var sueldo by remember { mutableStateOf("") }
    var hijos by remember { mutableStateOf("") }
    var datos by remember { mutableStateOf<Docente?>(null) }

    LaunchedEffect(true) {
        scope.launch {
            datos = dao.buscarPorCodigo(codigo)
//            if (datos!=null){
//
//            }
            //el codigo se ejecuta si datos no es nulo
            datos?.let{
                //mostrar valores en variables reactivas
                nombres = it.nombres
                apellidos = it.apellidos
                nomSexo = it.sexo
                sueldo = it.sueldo.toString()
                hijos = it.hijos.toString()
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbar)
        },
        topBar = {
            TopAppBar(
                title = { Text("Actualizar Docente") },
                navigationIcon = {
                    IconButton(
                        onClick = { onBack() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { espacio ->
        Column(
            modifier = Modifier
                .padding(espacio)
                .padding(15.dp)
        ) {
            OutlinedTextField(
                value = nombres,
                onValueChange = { nombres = it },
                label = { Text("Ingresar nombres") },
                modifier = Modifier
                    .fillMaxWidth()
            )

            OutlinedTextField(
                value = apellidos,
                onValueChange = { apellidos = it },
                label = { Text("Ingresar apellidos") },
                modifier = Modifier
                    .fillMaxWidth()
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                OutlinedTextField(
                    value = nomSexo,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("[Seleccione Sexo]") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    sexos.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = {
                                nomSexo = item
                                expanded = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = sueldo,
                onValueChange = { sueldo = it },
                label = { Text("Ingresar sueldo") },
                modifier = Modifier
                    .fillMaxWidth()
            )

            OutlinedTextField(
                value = hijos,
                onValueChange = { hijos = it },
                label = { Text("Ingresar hijos") },
                modifier = Modifier
                    .fillMaxWidth()
            )


            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Button(
                    onClick = {
                        scope.launch {
                            try {
                                dao.actualizar(
                                    // !! sirve para indicar que
                                    Docente(datos!!.codigo,nombres,apellidos,
                                        nomSexo,sueldo.toDouble(),hijos.toInt())
                                )
                                snackbar.showSnackbar("Docente actualizado")
                            }catch (e: Exception){
                                snackbar.showSnackbar("Error> ${e.message}")
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text("Actualizar")
                }
            }

        }


    }
}