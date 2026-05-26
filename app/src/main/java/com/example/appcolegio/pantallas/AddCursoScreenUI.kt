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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appcolegio.local.AppDatabase
import com.example.appcolegio.local.entidades.Curso
import kotlinx.coroutines.launch


//01:13:24
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdicionarCurso(onBack: () -> Unit, db: AppDatabase) {

    //obtener el DAO
    val dao = db.cursoDao()

    //crear objeto de una corrutina
    val scope = rememberCoroutineScope()

    //snackbar
    val snackbar=remember { SnackbarHostState() }

    var nombre by remember { mutableStateOf("") }
    var ciclos = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
    var numCiclo by remember { mutableStateOf("") }
    var expandedCiclo by remember { mutableStateOf(false) }
    var numCred by remember { mutableStateOf("") }
    var nomCarrera by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbar)
        },
        topBar = {
            TopAppBar(
                title = { Text("Registrar Curso") },
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
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Ingresar nombre") },
                modifier = Modifier
                    .fillMaxWidth()
            )

            ExposedDropdownMenuBox(
                expanded = expandedCiclo,
                onExpandedChange = { expandedCiclo = !expandedCiclo },
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                OutlinedTextField(
                    value = numCiclo,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("[Seleccione ciclo]") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expandedCiclo)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                )

                ExposedDropdownMenu(
                    expanded = expandedCiclo,
                    onDismissRequest = { expandedCiclo = false }
                ) {
                    ciclos.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = {
                                numCiclo = item
                                expandedCiclo = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = numCred,
                onValueChange = { numCred = it },
                label = { Text("Ingresar credito") },
                modifier = Modifier
                    .fillMaxWidth()
            )

            OutlinedTextField(
                value = nomCarrera,
                onValueChange = { nomCarrera = it },
                label = { Text("Ingresar carrera") },
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
                                dao.registrar(
                                    Curso(0,nombre, numCiclo, numCred, nomCarrera)
                                )
                                snackbar.showSnackbar("Curso registrado")
                            }catch (e: Exception){
                                snackbar.showSnackbar("Error> ${e.message}")
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text("Grabar")
                }
            }

        }


    }
}