package com.code.notes.data.repository

import com.code.notes.data.source.local.NotesDao
import com.code.notes.domain.common.NoteOrder
import com.code.notes.domain.common.OrderType
import com.code.notes.domain.model.Notes
import com.code.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepoImp(val dao:NotesDao):NoteRepository {
    override  fun getNotes(noteOrder: NoteOrder):Flow<List<Notes>>{
        return dao.getNotes()
    }


    override suspend fun getNote(id: Int): Notes? {
       return  dao.getNote(id)
    }

    override suspend fun insertNote(note: Notes) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Notes) {
        dao.deleteNote(note)
    }

}