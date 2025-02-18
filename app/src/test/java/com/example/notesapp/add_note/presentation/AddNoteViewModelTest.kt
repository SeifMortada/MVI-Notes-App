package com.example.notesapp.add_note.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notesapp.MainCoroutineRule
import com.example.notesapp.add_note.domain.usecases.UpsertNoteUseCase
import com.example.notesapp.core.data.repository.FakeNoteRepository
import com.example.notesapp.core.domain.repository.NoteRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddNoteViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var repository: NoteRepository
    private lateinit var viewModel: AddNoteViewModel

    @Before
    fun setUp() {
        repository = FakeNoteRepository()
        val upsertNoteUseCase = UpsertNoteUseCase(repository)
        viewModel = AddNoteViewModel(upsertNoteUseCase)
    }

    @Test
    fun `test upsert new note with empty title ,return false`() = runTest {
        val isInserted =
            viewModel.upsertNote(title = "", description = "description", imageUrl = "image")
        Truth.assertThat(isInserted).isFalse()
    }

    @Test
    fun `test upsert new note with empty description ,return false`() = runTest {
        val isInserted =
            viewModel.upsertNote(title = "title", description = "", imageUrl = "image")
        Truth.assertThat(isInserted).isFalse()
    }

    @Test
    fun `test upsert new note with empty imageUrl ,return true`() = runTest {
        val isInserted =
            viewModel.upsertNote(title = "title", description = "description", imageUrl = "")
        Truth.assertThat(isInserted).isTrue()
    }

    @Test
    fun `test upsert new note with valid data ,return true`() = runTest {
        val isInserted =
            viewModel.upsertNote(
                title = "title",
                description = "description",
                imageUrl = "imageUrl"
            )
        Truth.assertThat(isInserted).isTrue()
    }
}