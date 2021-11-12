package com.code.notes.presentation.notes

import com.code.notes.domain.common.NoteOrder
import com.code.notes.domain.common.OrderType
import com.code.notes.domain.model.Notes

data class NotesState(
    val notes: List<Notes> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val orderSectionVisible:Boolean = false
)
