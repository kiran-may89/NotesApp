package com.code.notes.domain.util.noteorder

import com.code.notes.domain.common.NoteOrder
import com.code.notes.domain.model.Notes

sealed class NoteEvent{
    data class  Order(val noteOrder: NoteOrder):NoteEvent()
    data class  DeleteNote(val note:Notes):NoteEvent()
    object RestoreNote:NoteEvent()
    object ToggleOrderSection:NoteEvent()
}
