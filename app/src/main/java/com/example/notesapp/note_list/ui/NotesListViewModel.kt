package com.example.notesapp.note_list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.core.domain.model.NoteItem
import com.example.notesapp.note_list.domain.use_case.DeleteNoteUseCase
import com.example.notesapp.note_list.domain.use_case.GetAllNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val getAllNotesUseCase: GetAllNotesUseCase
) : ViewModel() {

    private val _noteListState = MutableStateFlow<List<NoteItem>>(emptyList())
    val noteListState = _noteListState.asStateFlow()

    private val _orderByTitleState = MutableStateFlow<Boolean>(false)
    val orderByTitleState = _orderByTitleState.asStateFlow()

    fun loadNotes() = viewModelScope.launch {
        _noteListState.update {
            getAllNotesUseCase(orderByTitleState.value)
        }
    }

    fun deleteNote(noteItem: NoteItem) = viewModelScope.launch {
        deleteNoteUseCase(noteItem)
        loadNotes()
    }

    fun changeOrder() = viewModelScope.launch {
        _orderByTitleState.update { !it }
        loadNotes()
    }

}