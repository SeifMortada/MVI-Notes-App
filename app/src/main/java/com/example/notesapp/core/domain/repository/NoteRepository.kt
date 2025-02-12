package com.example.notesapp.core.domain.repository

import com.example.notesapp.core.domain.model.NoteItem

interface NoteRepository{
    suspend fun upsertNote(noteEntity: NoteItem)
    suspend fun deleteNote(noteEntity: NoteItem)
    suspend fun getAllNotes():List<NoteItem>

}