package com.example.appcolegio.retrofit.dao

import com.example.appcolegio.retrofit.entidades.CloudinaryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface CloudinaryApi {
    @Multipart
    @POST("v1_1/dlifmpg8t/image/upload")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @PartMap params: Map<String, @JvmSuppressWildcards RequestBody>
    ): CloudinaryResponse
}