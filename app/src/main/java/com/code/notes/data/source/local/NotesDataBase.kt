package com.code.notes.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.code.notes.domain.model.Notes

const val  NOTE_DATA_BASE = "notes_db"
@Database(entities = [Notes::class],version = 1)
abstract class NotesDataBase:RoomDatabase() {
    abstract val notesDao:NotesDao;

}