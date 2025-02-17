package com.example.notesapp.core.data.repository

import com.example.notesapp.core.domain.model.NoteItem
import com.example.notesapp.core.domain.repository.NoteRepository

class FakeAndroidNoteRepository() : NoteRepository {
    override suspend fun upsertNote(noteItem: NoteItem) {
    }

    override suspend fun deleteNote(noteItem: NoteItem) {
    }

    override suspend fun getAllNotes(): List<NoteItem> {
        return emptyList()
    }
}