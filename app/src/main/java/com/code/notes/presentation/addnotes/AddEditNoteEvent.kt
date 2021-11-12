package com.code.notes.presentation.addnotes

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent{
    data class EnteredTitle(val title:String):AddEditNoteEvent()
    data class EnteredContent(val content:String):AddEditNoteEvent()
    data class ChangeTitleFocus(val focus:FocusState):AddEditNoteEvent()
    data class ChangeContentFocus(val focus: FocusState):AddEditNoteEvent()
    data class ChangeColor(val color:Int):AddEditNoteEvent()
    object SaveNote:AddEditNoteEvent()

}