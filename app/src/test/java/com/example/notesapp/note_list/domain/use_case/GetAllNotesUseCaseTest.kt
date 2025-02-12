package com.example.notesapp.note_list.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notesapp.core.data.repository.FakeNoteRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetAllNotesUseCaseTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var getAllNotesUseCase: GetAllNotesUseCase


    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        getAllNotesUseCase = GetAllNotesUseCase(fakeNoteRepository)
        fakeNoteRepository.shouldFillTheList(true)
    }

    @Test
    fun `get notes from empty list, empty list`() = runTest {
        fakeNoteRepository.shouldFillTheList(false)
        val notes = getAllNotesUseCase.invoke(true)
        assertThat(notes).isEmpty()
    }

    @Test
    fun `get notes sort by title, sorted by title`() = runTest {
        val notes = getAllNotesUseCase.invoke(true)
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].title).isLessThan(notes[i + 1].title)
        }
    }

    @Test
    fun `get notes sort by date added, sorted by date added`() = runTest {
        val notes = getAllNotesUseCase.invoke(false)
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].dateAdded).isLessThan(notes[i + 1].dateAdded)
        }
    }
}