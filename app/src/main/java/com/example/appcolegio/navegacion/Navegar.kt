package com.example.appcolegio.navegacion

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import androidx.room.Room
import com.example.appcolegio.local.AppDatabase
import com.example.appcolegio.local.entidades.Docente
import com.example.appcolegio.pantallas.AdicionarAlumno
import com.example.appcolegio.pantallas.AdicionarCurso
import com.example.appcolegio.pantallas.AdicionarDocente
import com.example.appcolegio.pantallas.AdicionarMenu
import com.example.appcolegio.pantallas.EditarDocente
import com.example.appcolegio.pantallas.ListaAlumno
import com.example.appcolegio.pantallas.ListaCurso
import com.example.appcolegio.pantallas.ListaDocente
import com.example.appcolegio.pantallas.ListaMenu

data object ListDocente
data object AddDocente
data object AddCurso
data object ListCurso

data class EditDocente(val cod: Int)

data object ListMenu
data object AddMenu

data object ListAlumno
data object AddAlumno


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navegar() {

    val backStack = remember { mutableStateListOf<Any>(ListAlumno) }
    val context = LocalContext.current

    /* crear bd */
    val db = remember {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "colegio.db"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is ListDocente -> NavEntry(key) {
                    ListaDocente(
                        addDocente = {
                            backStack.add(AddDocente)
                        },
                        onCurso = {
                            backStack.clear()
                            backStack.add(ListCurso)
                        },
                        db = db,
                        datosDocente = {
                            backStack.add(EditDocente(it))
                        }
                    )
                }

                is ListMenu -> NavEntry(key){
                    ListaMenu(addMenu = {backStack.add(AddMenu)})
                }
                is AddMenu -> NavEntry(key){
                    AdicionarMenu(onBack = {backStack.removeLastOrNull()})
                }

                is ListAlumno -> NavEntry(key){
                    ListaAlumno(addAlumno = {backStack.add(AddAlumno)})
                }
                is AddAlumno -> NavEntry(key) {
                    AdicionarAlumno(onBack = { backStack.removeLastOrNull() }) // ← quita el comentario
                }

                is ListCurso -> NavEntry(key) {
                    ListaCurso(
                        addCurso = {
                            backStack.add(AddCurso)
                        },
                        onDocente = {
                            backStack.clear()
                            backStack.add(ListDocente)
                        },
                        db = db
                    )
                }
                is EditDocente -> NavEntry(key) {
                    EditarDocente(onBack = { backStack.removeLastOrNull() }, db = db,
                        codigo = key.cod)
                }

                is AddCurso -> NavEntry(key) {
                    AdicionarCurso(onBack = { backStack.removeLastOrNull() }, db = db)
                }

                is AddDocente -> NavEntry(key) {
                    AdicionarDocente(onBack = { backStack.removeLastOrNull() }, db = db)
                }

                else -> NavEntry(Unit) { Text("Unknown route") }
            }
        }
    )
}