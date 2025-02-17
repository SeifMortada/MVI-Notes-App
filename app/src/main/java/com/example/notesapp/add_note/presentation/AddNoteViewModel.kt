package com.example.notesapp.add_note.presentation

import androidx.lifecycle.ViewModel
import com.example.notesapp.add_note.domain.usecases.UpsertNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
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
class AddNoteViewModel @Inject constructor(private val upsetNoteUseCase: UpsertNoteUseCase) :
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
                TODO()
            }

            is AddNoteActions.PickImage -> {
                TODO()
            }

            is AddNoteActions.UpdateSearchImageQuery -> {
                TODO()
            }

            AddNoteActions.UpdateImagesDialogVisibility -> {
                TODO()
            }

            AddNoteActions.SaveNote -> {
                TODO()
            }
        }
    }

    suspend fun upsertNote(
        title: String,
        description: String,
        imageUrl: String
    ):Boolean{
        return upsetNoteUseCase.invoke(title, description, imageUrl)
    }


}