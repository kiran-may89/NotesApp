package com.code.notes.presentation.addnotes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.notes.domain.model.InvalidNotesException
import com.code.notes.domain.model.Notes
import com.code.notes.domain.usecases.NotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    val notesUseCase: NotesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _notesTitle = mutableStateOf(NoteTextFieldState(hint = "Enter Title...."))
    val notesTitle: State<NoteTextFieldState> = _notesTitle

    private val _notesContent = mutableStateOf(NoteTextFieldState(hint = "Enter Notes"))
    val notesContent: State<NoteTextFieldState> = _notesContent

    private val _noteColor = mutableStateOf<Int>(Notes.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { id ->
            if (id != -1) {

                viewModelScope.launch {
                    notesUseCase.getNote(id)?.also { note ->
                        currentNoteId = note.id
                        _notesTitle.value = notesTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _notesContent.value = notesContent.value.copy(
                            text = note.description,
                            isHintVisible = false
                        )
                        _noteColor.value = note.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.ChangeColor -> _noteColor.value = event.color
            is AddEditNoteEvent.ChangeContentFocus -> _notesContent.value =
                notesContent.value.copy(isHintVisible = event.focus.isFocused.not() && notesContent.value.text.isBlank())
            is AddEditNoteEvent.ChangeTitleFocus -> _notesTitle.value =
                notesTitle.value.copy(isHintVisible = event.focus.isFocused.not() && notesTitle.value.text.isBlank())
            is AddEditNoteEvent.EnteredContent -> _notesContent.value =
                notesContent.value.copy(text = event.content)
            is AddEditNoteEvent.EnteredTitle -> _notesTitle.value =
                notesTitle.value.copy(text = event.title)
            AddEditNoteEvent.SaveNote -> viewModelScope.launch {
                try {
                    notesUseCase.insertNote(
                        Notes(

                            title = notesTitle.value.text,
                            description = notesContent.value.text,
                            color = noteColor.value,
                            timeStamp = System.currentTimeMillis(),
                            id = currentNoteId
                        )
                    )
                    _eventFlow.emit(UiEvent.SaveNote)
                } catch (e: InvalidNotesException) {
                    _eventFlow.emit(UiEvent.ShowSnackBar(e.message ?: "Unknown Error"))

                }

            }
        }
    }


    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }


}