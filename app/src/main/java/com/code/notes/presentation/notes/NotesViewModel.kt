package com.code.notes.presentation.notes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.notes.domain.common.NoteOrder
import com.code.notes.domain.common.OrderType
import com.code.notes.domain.model.Notes
import com.code.notes.domain.usecases.NotesUseCase
import com.code.notes.domain.util.noteorder.NoteEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(val notesUseCase: NotesUseCase):ViewModel() {
    private var lastDeletedNote:Notes?= null

    private val _state = mutableStateOf(NotesState())
    val state:State<NotesState> get() = _state
    private var  getNotesJob:Job?=null

    init {

        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event:NoteEvent){
        when(event){
            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {notesUseCase.deleteNote(event.note)  }
                lastDeletedNote = event.note
            }
            is NoteEvent.Order ->{
                if(state.value.noteOrder::class ==event.noteOrder::class && state.value.noteOrder.orderType == event.noteOrder.orderType){
                    return
                }
                getNotes(event.noteOrder)
            }


            NoteEvent.RestoreNote -> {
                viewModelScope.launch {
                    notesUseCase.insertNote(lastDeletedNote?:return@launch)
                    lastDeletedNote = null
                }

            }
            NoteEvent.ToggleOrderSection -> {
                _state.value = _state.value.copy(
                    orderSectionVisible =  !state.value.orderSectionVisible
                )

            }
        }
    }
    private  fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = notesUseCase.getNotes(noteOrder).onEach { notes-> _state.value =  state.value.copy(notes = notes, noteOrder = noteOrder) }.launchIn(viewModelScope)
    }

}