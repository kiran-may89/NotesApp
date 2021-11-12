package com.code.notes.data.source.local

import androidx.room.*
import com.code.notes.domain.model.Notes
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * from Notes")
     fun getNotes(): Flow<List<Notes>>
    @Query("SELECT * from Notes WHERE id = :id")
    suspend fun getNote(id:Int):Notes?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Notes)

    @Delete
    suspend fun deleteNote(note: Notes)
}