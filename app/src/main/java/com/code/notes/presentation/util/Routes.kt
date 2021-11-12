package com.code.notes.presentation.util

sealed class Routes(val route: String) {
    object NoteScreen : Routes("notes_screen")
    object AddEditNoteScreen : Routes("add_edit_note_screen")

}
