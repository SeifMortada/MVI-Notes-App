package com.example.notesapp.add_note.presentation.util

sealed class Resource< T>(
    val data: T? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(data: T? = null) : Resource<T>(data)
    class Loading<T>(val isLoading: Boolean = true) : Resource<T>(null)
}