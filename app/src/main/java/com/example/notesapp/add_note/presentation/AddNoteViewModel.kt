package com.example.notesapp.add_note.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.add_note.domain.usecases.SearchImagesUseCase
import com.example.notesapp.add_note.domain.usecases.UpsertNoteUseCase
import com.example.notesapp.add_note.presentation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddNoteState(
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val isImagesDialogVisible: Boolean = false,
    val imageList: List<String> = emptyList(),
    val searchImageQuery: String = "",
    val isLoadingImages: Boolean = false
)

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val upsetNoteUseCase: UpsertNoteUseCase,
    private val searchImagesUseCase: SearchImagesUseCase
) :
    ViewModel() {

    private val _addNoteState = MutableStateFlow(AddNoteState())
    val addNoteState = _addNoteState.asStateFlow()

    private val _noteSavedChannel = Channel<Boolean>()
    val noteSavedFlow = _noteSavedChannel.receiveAsFlow()

    fun onAction(action: AddNoteActions) {
        when (action) {
            is AddNoteActions.UpdateTitle -> {
                TODO()
            }

            is AddNoteActions.UpdateDescription -> {
                _addNoteState.update {
                    it.copy(description = action.newDescription)
                }
            }

            is AddNoteActions.PickImage -> {
                _addNoteState.update {
                    it.copy(imageUrl = action.imageUrl)
                }
            }

            is AddNoteActions.UpdateSearchImageQuery -> {
                _addNoteState.update {
                    it.copy(searchImageQuery = action.newImageQuery)
                }
                searchImages(action.newImageQuery)
            }

            AddNoteActions.UpdateImagesDialogVisibility -> {
                _addNoteState.update {
                    it.copy(isImagesDialogVisible = !it.isImagesDialogVisible)
                }
            }

            AddNoteActions.SaveNote -> {
                viewModelScope.launch {
                    val isSaved = upsertNote(
                        title = addNoteState.value.title,
                        description = addNoteState.value.description,
                        imageUrl = addNoteState.value.imageUrl
                    )
                    _noteSavedChannel.send(isSaved)
                }
            }
        }
    }

    private var searchJob: Job? = null
    fun searchImages(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            searchImagesUseCase(query).collect { result ->
                when (result) {
                    is Resource.Error -> _addNoteState.update { it.copy(imageList = emptyList()) }
                    is Resource.Loading -> _addNoteState.update { it.copy(isLoadingImages = result.isLoading) }
                    is Resource.Success -> _addNoteState.update {
                        it.copy(
                            imageList = result.data?.images ?: emptyList()
                        )
                    }
                }
            }
        }
    }

    suspend fun upsertNote(
        title: String,
        description: String,
        imageUrl: String
    ): Boolean {
        return upsetNoteUseCase.invoke(title, description, imageUrl)
    }


}