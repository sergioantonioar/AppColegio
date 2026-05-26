package com.example.appcolegio.navegacion


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MenuInferior(
    onDocente: () -> Unit,
    onCurso: () -> Unit,

    ) {
    BottomAppBar {
        Text(
            "Inicio",
            modifier = Modifier
                .padding(15.dp)
        )

        Text(
            "Docente",
            modifier = Modifier
                .padding(15.dp)
                .clickable{
                    onDocente()
                }
        )

        Text(
            "Curso",
            modifier = Modifier
                .padding(15.dp)
                .clickable{
                    onCurso()
                }
        )

    }
}