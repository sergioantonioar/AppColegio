package com.example.appcolegio.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

//esta funcion devuelve ubicacion de la imagen
fun createImageUri(context: Context): Uri {
    val imageFile = File(
        context.cacheDir,
        "menu_photo.jpg"
    )
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        imageFile
    )
}