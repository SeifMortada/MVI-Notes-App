package com.example.notesapp.note_list.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notesapp.core.data.repository.FakeNoteRepository
import com.example.notesapp.core.domain.model.NoteItem
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DeleteNoteUseCaseTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var deleteNoteUseCase: DeleteNoteUseCase


    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        deleteNoteUseCase = DeleteNoteUseCase(fakeNoteRepository)
        fakeNoteRepository.shouldFillTheList(true)
    }

    @Test
    fun `delete note , return list without the note`() = runTest {
        val note = NoteItem("title", "desc", "imgUrl", 10)
        fakeNoteRepository.upsertNote(note)
        assertThat(fakeNoteRepository.getAllNotes().contains(note)).isTrue()
        deleteNoteUseCase.invoke(note)
        assertThat(fakeNoteRepository.getAllNotes().contains(note)).isFalse()
    }
}