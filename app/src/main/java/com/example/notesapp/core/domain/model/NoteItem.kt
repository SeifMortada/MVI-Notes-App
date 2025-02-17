package com.example.notesapp.core.domain.model

data class NoteItem(
    var title: String,
    var description: String,
    var imageUrl: String,
    var dateAdded: Long
)
