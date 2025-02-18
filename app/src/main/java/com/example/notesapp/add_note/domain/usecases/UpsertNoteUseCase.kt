package com.example.notesapp.add_note.domain.usecases

import com.example.notesapp.core.domain.model.NoteItem
import com.example.notesapp.core.domain.repository.NoteRepository
import javax.inject.Inject

class UpsertNoteUseCase @Inject constructor(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(title: String, description: String, imageUrl: String): Boolean {
        return if (title.isEmpty() || description.isEmpty())
            false
        else {
            noteRepository.upsertNote(
                NoteItem(
                    title,
                    description,
                    imageUrl,
                    System.currentTimeMillis()
                )
            )
            true
        }
    }
}