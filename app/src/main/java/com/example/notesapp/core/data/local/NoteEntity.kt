package com.example.notesapp.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title: String,
    var description: String,
    var imageUrl: String,
    var dateAdded: Long
)
