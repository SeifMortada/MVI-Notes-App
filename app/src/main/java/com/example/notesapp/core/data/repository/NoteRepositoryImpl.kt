package com.example.notesapp.core.data.repository

import com.example.notesapp.core.data.local.NoteDb
import com.example.notesapp.core.data.mapper.toNoteEntityForDelete
import com.example.notesapp.core.data.mapper.toNoteEntityForInsert
import com.example.notesapp.core.data.mapper.toNoteItem
import com.example.notesapp.core.domain.model.NoteItem
import com.example.notesapp.core.domain.repository.NoteRepository

class NoteRepositoryImpl(noteDb: NoteDb) : NoteRepository {
    private val noteDao = noteDb.noteDao
    override suspend fun upsertNote(noteItem: NoteItem) {
        noteDao.upsertNoteEntity(noteItem.toNoteEntityForInsert())
    }

    override suspend fun deleteNote(noteItem: NoteItem) {
        noteDao.deleteNoteEntity(noteItem.toNoteEntityForDelete())
    }

    override suspend fun getAllNotes(): List<NoteItem> {
        return noteDao.getAllNoteEntities().map { it.toNoteItem() }
    }
}