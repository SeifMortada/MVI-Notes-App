package com.example.notesapp.add_note.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notesapp.MainCoroutineRule
import com.example.notesapp.add_note.domain.usecases.SearchImagesUseCase
import com.example.notesapp.add_note.domain.usecases.UpsertNoteUseCase
import com.example.notesapp.core.data.repository.FakeImagesRepository
import com.example.notesapp.core.data.repository.FakeNoteRepository
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddNoteViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var notesRepository: FakeNoteRepository
    private lateinit var imagesRepository: FakeImagesRepository
    private lateinit var viewModel: AddNoteViewModel

    @Before
    fun setUp() {
        notesRepository = FakeNoteRepository()
        imagesRepository=FakeImagesRepository()
        val upsertNoteUseCase = UpsertNoteUseCase(notesRepository)
        val searchImagesUseCase = SearchImagesUseCase(imagesRepository)
        viewModel = AddNoteViewModel(upsertNoteUseCase, searchImagesUseCase)
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
        assertThat(isInserted).isTrue()
    }

    @Test
    fun `test search image with empty query,image list is empty`() = runTest {
        viewModel.searchImages("")
        advanceUntilIdle()
        assertThat(viewModel.addNoteState.value.imageList).isEmpty()
    }

    @Test
    fun `test search image with valid query but no result,image list is empty`() = runTest {
        imagesRepository.shouldReturnError(true)
        viewModel.searchImages("Query")
        advanceUntilIdle()
        assertThat(viewModel.addNoteState.value.imageList).isEmpty()
    }

    @Test
    fun `test search image with valid query,image list is not empty`() = runTest {
        imagesRepository.shouldReturnError(false)
        viewModel.searchImages("Query")
        advanceUntilIdle()
        assertThat(viewModel.addNoteState.value.imageList).isNotEmpty()
    }
}