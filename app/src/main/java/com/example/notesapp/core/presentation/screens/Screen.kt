package com.example.notesapp.core.presentation.screens

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object NoteList : Screen

    @Serializable
    data object AddNote : Screen
}