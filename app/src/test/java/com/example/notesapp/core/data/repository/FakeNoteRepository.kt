package com.example.notesapp.core.data.repository

import com.example.notesapp.core.domain.model.NoteItem
import com.example.notesapp.core.domain.repository.NoteRepository

class FakeNoteRepository() : NoteRepository {
    private var noteItems = mutableListOf<NoteItem>()

    fun shouldFillTheList(should: Boolean) {
        noteItems = if (should) {
            mutableListOf(
                NoteItem("title 1", "desc 1", "url 1", 1),
                NoteItem("title 2", "desc 2", "url 2", 2),
                NoteItem("title 3", "desc 3", "url 3", 3),
            )
        } else mutableListOf()

    }

    override suspend fun upsertNote(noteItem: NoteItem) {
        noteItems.add(noteItem)
    }

    override suspend fun deleteNote(noteItem: NoteItem) {
        noteItems.remove(noteItem)
    }

    override suspend fun getAllNotes(): List<NoteItem> {
        return noteItems
    }
}