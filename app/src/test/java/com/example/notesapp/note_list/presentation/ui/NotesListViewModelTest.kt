package com.example.notesapp.note_list.presentation.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notesapp.MainCoroutineRule
import com.example.notesapp.core.data.repository.FakeNoteRepository
import com.example.notesapp.core.domain.model.NoteItem
import com.example.notesapp.note_list.domain.use_case.DeleteNoteUseCase
import com.example.notesapp.note_list.domain.use_case.GetAllNotesUseCase
import com.example.notesapp.note_list.ui.NotesListViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NotesListViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var deleteNoteUseCase: DeleteNoteUseCase
    private lateinit var getAllNotesUseCase: GetAllNotesUseCase
    private lateinit var notesListViewModel: NotesListViewModel

    @Before
    fun setUpViewModel() {
        fakeNoteRepository = FakeNoteRepository()
        deleteNoteUseCase = DeleteNoteUseCase(fakeNoteRepository)
        getAllNotesUseCase = GetAllNotesUseCase(fakeNoteRepository)
        notesListViewModel = NotesListViewModel(
            deleteNoteUseCase, getAllNotesUseCase
        )
    }

    @Test
    fun `get notes from an empty list, note list is empty`() = runTest {
        fakeNoteRepository.shouldFillTheList(false)
        notesListViewModel.loadNotes()

        val noteListState = notesListViewModel.noteListState.value
        assertThat(noteListState.isEmpty()).isTrue()

    }

    @Test
    fun `get notes from an filled list, note list is not empty`() = runTest {
        fakeNoteRepository.shouldFillTheList(true)
        notesListViewModel.loadNotes()
        advanceUntilIdle()
        val noteListState = notesListViewModel.noteListState.value
        assertThat(noteListState.isNotEmpty()).isTrue()

    }

    @Test
    fun `delete note from list , note is deleted`() = runTest {
        fakeNoteRepository.shouldFillTheList(true)
        val note =
            NoteItem(title = "test", description = "test", imageUrl = "test", dateAdded = 123)
        fakeNoteRepository.upsertNote(note)

        notesListViewModel.loadNotes()
        advanceUntilIdle()

        var noteListState = notesListViewModel.noteListState.value
        assertThat(noteListState.contains(note)).isTrue()

        notesListViewModel.deleteNote(note)
        advanceUntilIdle()

        noteListState = notesListViewModel.noteListState.value
        assertThat(noteListState.contains(note)).isFalse()
    }

    @Test
    fun `sort note by date , notes are sorted by date`() = runTest {
        fakeNoteRepository.shouldFillTheList(true)
        notesListViewModel.loadNotes()
        advanceUntilIdle()
        var notes = notesListViewModel.noteListState.value
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].dateAdded).isLessThan(notes[i + 1].dateAdded)
        }
        notesListViewModel.changeOrder()
        advanceUntilIdle()

        notes = notesListViewModel.noteListState.value
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].dateAdded).isLessThan(notes[i + 1].dateAdded)
        }
    }

    @Test
    fun `sort note by title , notes are sorted by title`() = runTest {
        fakeNoteRepository.shouldFillTheList(true)

        notesListViewModel.changeOrder()
        advanceUntilIdle()
        val notes = notesListViewModel.noteListState.value
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].title).isLessThan(notes[i + 1].title)
        }
    }
}