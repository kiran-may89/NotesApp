package com.code.notes.di

import android.app.Application
import androidx.room.Room
import com.code.notes.data.repository.NoteRepoImp
import com.code.notes.data.source.local.NOTE_DATA_BASE
import com.code.notes.data.source.local.NotesDao
import com.code.notes.data.source.local.NotesDataBase
import com.code.notes.domain.repository.NoteRepository
import com.code.notes.domain.usecases.NotesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDataBase(app: Application): NotesDataBase {
        return Room.databaseBuilder(app, NotesDataBase::class.java, NOTE_DATA_BASE).build()
    }

    @Provides
    @Singleton
    fun providesRepository(dataBase: NotesDataBase): NoteRepository {
        return NoteRepoImp(dataBase.notesDao)
    }

    @Provides
    @Singleton
    fun providesNotesUseCase(repo:NoteRepository):NotesUseCase{
        return NotesUseCase(repo)
    }

}