package com.example.notesapp.add_note.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notesapp.MainCoroutineRule
import com.example.notesapp.core.data.repository.FakeNoteRepository
import com.example.notesapp.core.domain.repository.NoteRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class UpsertNoteUseCaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    private lateinit var noteRepository: NoteRepository
    private lateinit var upsertNoteUseCase: UpsertNoteUseCase

    @Before
    fun setUp() {
        noteRepository = FakeNoteRepository()
        upsertNoteUseCase = UpsertNoteUseCase(noteRepository)
    }

    @Test
    fun `test upsert new note with empty title ,return false`() = runTest {
        val isInserted =
            upsertNoteUseCase.invoke(title = "", description = "description", imageUrl = "image")
        assertThat(isInserted).isFalse()
    }

    @Test
    fun `test upsert new note with empty description ,return false`() = runTest {
        val isInserted =
            upsertNoteUseCase.invoke(title = "title", description = "", imageUrl = "image")
        assertThat(isInserted).isFalse()
    }

    @Test
    fun `test upsert new note with empty imageUrl ,return true`() = runTest {
        val isInserted =
            upsertNoteUseCase.invoke(title = "title", description = "description", imageUrl = "")
        assertThat(isInserted).isTrue()
    }

    @Test
    fun `test upsert new note with valid data ,return true`() = runTest {
        val isInserted =
            upsertNoteUseCase.invoke(
                title = "title",
                description = "description",
                imageUrl = "imageUrl"
            )
        assertThat(isInserted).isTrue()
    }
}