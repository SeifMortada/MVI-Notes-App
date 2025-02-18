package com.example.notesapp.core.data.remote.api

import com.example.notesapp.core.data.remote.RemoteConstants
import com.example.notesapp.core.data.remote.dto.ImageListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesApi {
    @GET("/api/")
    suspend fun searchImages(
        @Query("q") query: String,
        @Query("key") apiKey: String = RemoteConstants.API_KEY
    ): ImageListDto?
}