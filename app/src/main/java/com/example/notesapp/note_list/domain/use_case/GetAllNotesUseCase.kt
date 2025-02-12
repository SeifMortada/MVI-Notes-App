package com.example.notesapp.note_list.domain.use_case

import com.example.notesapp.core.domain.model.NoteItem
import com.example.notesapp.core.domain.repository.NoteRepository

class GetAllNotesUseCase(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(isOrderByTitle: Boolean): List<NoteItem> {

        return if (isOrderByTitle)
            noteRepository.getAllNotes().sortedBy { it.title.lowercase()}
        else noteRepository.getAllNotes().sortedBy { it.dateAdded }

    }
}