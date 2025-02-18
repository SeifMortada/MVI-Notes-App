package com.example.notesapp.add_note.domain.usecases

import com.example.notesapp.add_note.presentation.util.Resource
import com.example.notesapp.core.domain.model.Images
import com.example.notesapp.core.domain.repository.ImagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchImagesUseCase @Inject constructor(
    private val imageRepository: ImagesRepository
) {
    suspend operator fun invoke(
        query: String
    ): Flow<Resource<Images>> {
        return flow {
            emit(Resource.Loading(true))

            if (query.isBlank()) {
                emit(Resource.Error())
                emit(Resource.Loading(false))
                return@flow
            }
            val images =
                try {
                    imageRepository.searchImages(query)
                } catch (e: Exception) {
                    e.printStackTrace()
                    emit(Resource.Error())
                    emit(Resource.Loading(false))
                    return@flow
                }
            images?.let {
                emit(Resource.Success(it))
                emit(Resource.Loading(false))
                return@flow
            } ?: emit(Resource.Error())
            emit(Resource.Loading(false))

        }
    }
}