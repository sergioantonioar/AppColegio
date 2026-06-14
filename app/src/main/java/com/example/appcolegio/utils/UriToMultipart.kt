package com.example.appcolegio.utils

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

//Devuelve la ubicacion de la imagen local
fun uriToMultipart(context:Context,uri:Uri):MultipartBody.Part{

    //recibe la ubicacion uri para devolver bytes
    val inputStream =
        context.contentResolver.openInputStream(uri)
    val bytes = inputStream!!.readBytes()

    //indica que son bytes de imagen
    val requestBody =
        bytes.toRequestBody(
            "image/*".toMediaType()
        )

    //se crea valores predeterminados
    return MultipartBody.Part.createFormData(
        "file",
        "foto.jpg",
        requestBody
    )
}