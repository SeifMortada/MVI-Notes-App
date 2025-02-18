package com.example.notesapp.add_note.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notesapp.add_note.presentation.util.Resource
import com.example.notesapp.core.data.repository.FakeImagesRepository
import com.example.notesapp.core.domain.repository.ImagesRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchImagesUseCaseTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var searchImagesUseCase: SearchImagesUseCase
    private lateinit var fakeImagesRepository: FakeImagesRepository

    @Before
    fun setup() {
        fakeImagesRepository = FakeImagesRepository()
        searchImagesUseCase = SearchImagesUseCase(fakeImagesRepository)
    }

    @Test
    fun `test searchImages_withEmptyQuery_returnError`() = runTest {
        val query = ""
        searchImagesUseCase(query).collectLatest { result ->
            when (result) {
                is Resource.Error -> {
                    assertThat(result.data == null).isTrue()
                }

                is Resource.Loading -> Unit
                is Resource.Success -> Unit
            }
        }
    }

    @Test
    fun `test searchImages_withNetworkError_returnError`() = runTest {
        fakeImagesRepository.shouldReturnError(true)
        val query = "Query"
        searchImagesUseCase(query).collectLatest { result ->
            when (result) {
                is Resource.Error -> {
                    assertThat(result.data == null).isTrue()
                }

                is Resource.Loading -> Unit
                is Resource.Success -> Unit
            }
        }
    }

    @Test
    fun `test searchImages_withValidQuery_returnSuccess`() = runTest {
        fakeImagesRepository.shouldReturnError(false)
        val query = "Query"
        searchImagesUseCase(query).collectLatest { result ->
            when (result) {
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
                is Resource.Success -> {
                    assertThat(result.data != null).isTrue()
                }
            }
        }
    }

    @Test
    fun `test searchImages_withValidQuery_listIsNotEmpty`() = runTest {
        fakeImagesRepository.shouldReturnError(false)
        val query = "Query"
        searchImagesUseCase(query).collectLatest { result ->
            when (result) {
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
                is Resource.Success -> {
                    assertThat(result.data?.images?.size!! > 0).isTrue()
                }
            }
        }
    }
}