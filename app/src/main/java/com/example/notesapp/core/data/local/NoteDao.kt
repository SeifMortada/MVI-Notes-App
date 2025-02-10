package com.example.notesapp.core.data.local

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

interface NoteDao {

    @Upsert
    suspend fun upsertNoteEntity(noteEntity: NoteEntity)

    @Query("SELECT * FROM noteentity")
    suspend fun getAllNoteEntities(): List<NoteEntity>

    @Delete
    suspend fun deleteNoteEntity(noteEntity: NoteEntity)
}