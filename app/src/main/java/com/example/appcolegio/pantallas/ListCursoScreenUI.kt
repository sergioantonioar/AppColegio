package com.example.appcolegio.pantallas

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import com.example.appcolegio.local.AppDatabase
import com.example.appcolegio.local.entidades.Docente
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.appcolegio.local.entidades.Curso
import com.example.appcolegio.navegacion.MenuInferior
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaCurso(
    addCurso: () -> Unit,
    onDocente: () -> Unit,
    db: AppDatabase
) {

    val dao = db.cursoDao()
    val scope = rememberCoroutineScope()

    var lista by remember {
        mutableStateOf(listOf<Curso>())
    }

    //para se ejecute una sola vez por el composable y no haga cuello de botella
    LaunchedEffect(true) {
        scope.launch {
            lista = dao.listar()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Curso") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomAppBar {
                MenuInferior(
                    onCurso = {},
                    onDocente = {onDocente()}
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { addCurso() },
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
                        Text("Nombres: ${bean.nombre}")

                    }
                }
            }


        }

    }
}
