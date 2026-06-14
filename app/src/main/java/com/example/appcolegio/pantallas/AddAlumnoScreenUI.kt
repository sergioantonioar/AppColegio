package com.example.appcolegio.pantallas

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.appcolegio.local.AppDatabase
import com.example.appcolegio.local.entidades.Docente
import com.example.appcolegio.retrofit.CloudinaryClient
import com.example.appcolegio.retrofit.RetrofitCliente
import com.example.appcolegio.retrofit.entidades.Alumno
import com.example.appcolegio.retrofit.entidades.Menu
import com.example.appcolegio.utils.createImageUri
import com.example.appcolegio.utils.uriToMultipart
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


//01:13:24
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdicionarAlumno(onBack: () -> Unit) {

    val scope = rememberCoroutineScope()
    val snackbar = remember { SnackbarHostState() }

    var nombre by remember { mutableStateOf("") }
    var paterno by remember { mutableStateOf("") }
    var materno by remember { mutableStateOf("") }
    var numeroHermanos by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }

    // Dropdown sexo
    val sexos = listOf("Masculino", "Femenino")
    var expandedSexo by remember { mutableStateOf(false) }
    var sexo by remember { mutableStateOf("") }

    // DatePicker
    var mostrarDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    val context = LocalContext.current

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageCaptured by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) {
        if (it) {
            imageCaptured = it
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbar) },
        topBar = {
            TopAppBar(
                title = { Text("Registrar Alumno") },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    scope.launch {
                        try {
                            var urlImagen = ""
                            if (imageCaptured) {
                                val part = uriToMultipart(context, imageUri!!)
                                val params = mutableMapOf<String, RequestBody>()
                                params["upload_preset"] =
                                    "alumno_preset".toRequestBody("text/plain".toMediaType())
                                params["folder"] =
                                    "alumnos".toRequestBody("text/plain".toMediaType())

                                val response = CloudinaryClient.api.uploadImage(part, params)
                                urlImagen = response.secure_url
                            }

                            RetrofitCliente.alumnoApi.registrarAlumnos(
                                Alumno(
                                    codigo = 0,
                                    nombre = nombre,
                                    paterno = paterno,
                                    materno = materno,
                                    sexo = sexo,
                                    fechaNacimiento = fechaNacimiento,
                                    numeroHermanos = numeroHermanos.toIntOrNull() ?: 0,
                                    foto = urlImagen
                                )
                            )
                            snackbar.showSnackbar("Alumno registrado")
                        } catch (e: Exception) {
                            snackbar.showSnackbar("Error: ${e.message}")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text("Grabar")
            }
        }
    ) { espacio ->

        LazyColumn(
            modifier = Modifier.padding(espacio),
            contentPadding = PaddingValues(15.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
        ) {
            item {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Ingresar nombre") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                OutlinedTextField(
                    value = paterno,
                    onValueChange = { paterno = it },
                    label = { Text("Ingresar apellido paterno") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                OutlinedTextField(
                    value = materno,
                    onValueChange = { materno = it },
                    label = { Text("Ingresar apellido materno") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Dropdown Sexo
            item {
                ExposedDropdownMenuBox(
                    expanded = expandedSexo,
                    onExpandedChange = { expandedSexo = !expandedSexo },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = sexo,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("[Seleccione sexo]") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expandedSexo)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                    )
                    ExposedDropdownMenu(
                        expanded = expandedSexo,
                        onDismissRequest = { expandedSexo = false }
                    ) {
                        sexos.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item) },
                                onClick = {
                                    sexo = item
                                    expandedSexo = false
                                }
                            )
                        }
                    }
                }
            }

            // Fecha de nacimiento
            item {
                OutlinedTextField(
                    value = fechaNacimiento,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Fecha de nacimiento") },
                    trailingIcon = {
                        IconButton(onClick = { mostrarDatePicker = true }) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = null
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = numeroHermanos,
                    onValueChange = { numeroHermanos = it },
                    label = { Text("Ingresar número hermanos") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Column {
                    Button(
                        onClick = {
                            val uri = createImageUri(context)
                            imageUri = uri
                            launcher.launch(uri)
                        }
                    ) {
                        Text("Tomar Foto")
                    }
                    if (imageCaptured && imageUri != null) {
                        AsyncImage(
                            model = imageUri,
                            contentDescription = null,
                            modifier = Modifier.size(250.dp)
                        )
                    }
                }
            }

        }
    }

    // DatePicker Dialog
    if (mostrarDatePicker) {
        DatePickerDialog(
            onDismissRequest = { mostrarDatePicker = false },
            confirmButton = {
                Button(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        fechaNacimiento = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.of("UTC"))
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    }
                    mostrarDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                OutlinedButton(onClick = { mostrarDatePicker = false }) {
                    Text("CANCEL")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}