package com.code.notes.domain.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.code.notes.domain.common.NoteOrder
import com.code.notes.domain.model.Notes
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes(noteOrder: NoteOrder): Flow<List<Notes>>

    suspend fun getNote(id:Int): Notes?

    suspend fun insertNote(note: Notes)


    suspend fun deleteNote(note: Notes)
}