package com.example.notesapp.add_note.presentation

sealed interface AddNoteActions {
    data class UpdateTitle(val newTitle: String) : AddNoteActions
    data class UpdateDescription(val newDescription: String) : AddNoteActions
    data class UpdateSearchImageQuery(val newImageQuery: String) : AddNoteActions
    data class PickImage(val imageUrl: String) : AddNoteActions

    data object UpdateImagesDialogVisibility : AddNoteActions
    data object SaveNote : AddNoteActions


}