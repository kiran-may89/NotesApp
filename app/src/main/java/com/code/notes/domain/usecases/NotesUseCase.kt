package com.code.notes.domain.usecases

import androidx.compose.ui.text.toLowerCase
import com.code.notes.domain.common.NoteOrder
import com.code.notes.domain.common.OrderType
import com.code.notes.domain.model.InvalidNotesException
import com.code.notes.domain.model.Notes
import com.code.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.lang.Exception
import java.util.*

class NotesUseCase(private val repository: NoteRepository) {

     fun getNotes(noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)): Flow<List<Notes>> {
        return repository.getNotes(noteOrder).map { notes ->
            when (noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase(Locale.getDefault()) }
                        is NoteOrder.Date -> notes.sortedBy { it.timeStamp }
                        is NoteOrder.Color -> notes.sortedBy { it.color}
                    }


                }
                is OrderType.Descending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase(Locale.getDefault()) }
                        is NoteOrder.Date -> notes.sortedByDescending { it.timeStamp }
                        is NoteOrder.Color -> notes.sortedByDescending { it.color}
                    }

                }

            }
        }

    }

    suspend fun getNote(id: Int)= repository.getNote(id)
    suspend fun  deleteNote(notes: Notes){
        repository.deleteNote(notes)
    }

    @Throws(InvalidNotesException::class)
    suspend fun insertNote(notes: Notes){
        if (notes.title.isNullOrBlank()){
            throw  InvalidNotesException("The Title of the notes can't be empty")
        }
        if(notes.description.isNullOrBlank()){
            throw  InvalidNotesException("The description of the notes can't be empty")
        }
        repository.insertNote(notes)
    }
}