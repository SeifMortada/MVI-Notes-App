package com.example.notesapp.note_list.domain.use_case

import com.example.notesapp.core.domain.model.NoteItem
import com.example.notesapp.core.domain.repository.NoteRepository

class DeleteNoteUseCase(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(note:NoteItem){
        noteRepository.deleteNote(note)
    }
}