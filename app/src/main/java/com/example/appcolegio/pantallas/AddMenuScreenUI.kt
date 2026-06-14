package com.example.appcolegio.pantallas

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.appcolegio.local.AppDatabase
import com.example.appcolegio.local.entidades.Docente
import com.example.appcolegio.retrofit.CloudinaryClient
import com.example.appcolegio.retrofit.RetrofitCliente
import com.example.appcolegio.retrofit.entidades.Menu
import com.example.appcolegio.utils.createImageUri
import com.example.appcolegio.utils.uriToMultipart
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody


//01:13:24
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdicionarMenu(onBack: () -> Unit) {

    //crear objeto de una corrutina
    val scope = rememberCoroutineScope()

    //snackbar
    val snackbar = remember { SnackbarHostState() }

    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    val categorias = listOf(
        "Entradas",
        "Platos de Fondo",
        "Comida Criolla",
        "Comida Marina",
        "Menú Ejecutivo",
        "Comida Vegetariana"
    )
    var expanded by remember { mutableStateOf(false) }
    var nomCategoria by remember { mutableStateOf("") }
    var sueldo by remember { mutableStateOf("") }
    var hijos by remember { mutableStateOf("") }

    val context = LocalContext.current

    //la ubicacion de la imagen
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    //verifica la aceptacion de la imagen
    var imageCaptured by remember {
        mutableStateOf(false)
    }

    //para que se pueda activar la camara
    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture()
        ) {
            if (it) {
                imageCaptured = it
            }
        }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbar)
        },
        topBar = {
            TopAppBar(
                title = { Text("Registrar Menu") },
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

//            OutlinedTextField(
//                value = precio,
//                onValueChange = { precio = it },
//                label = { Text("Ingresar precio") },
//                modifier = Modifier
//                    .fillMaxWidth()
//            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                OutlinedTextField(
                    value = nomCategoria,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("[Seleccione Categoria]") },
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
                    categorias.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = {
                                nomCategoria = item
                                expanded = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = stock,
                onValueChange = { stock = it },
                label = { Text("Ingresar stock") },
                modifier = Modifier
                    .fillMaxWidth()
            )

            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Ingresar precio") },
                modifier = Modifier
                    .fillMaxWidth()
            )
            Column(

            ) {

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


            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Button(
                    onClick = {
                        scope.launch {
                            try {
                                var urlImagen = ""
                                if(imageCaptured){
                                    val part = uriToMultipart(context,imageUri!!)
                                    val params = mutableMapOf<String, RequestBody>()
                                    params["upload_preset"] =
                                        "menu_preset".toRequestBody("text/plain".toMediaType())
                                    params["folder"] =
                                        "menus".toRequestBody("text/plain".toMediaType())

                                    val response = CloudinaryClient.api.uploadImage(part,params)
                                    urlImagen = response.secure_url
                                }


                                RetrofitCliente.menuApi.registrarMenus(
                                    Menu(
                                        0,
                                        nombre,
                                        nomCategoria,
                                        stock.toInt(),
                                        precio.toDouble(),
                                        urlImagen
                                    )
                                )
                                snackbar.showSnackbar("Docente registrado")
                            } catch (e: Exception) {
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