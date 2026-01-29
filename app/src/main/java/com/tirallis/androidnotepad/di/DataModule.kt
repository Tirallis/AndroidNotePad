package com.tirallis.androidnotepad.di

import android.content.Context
import com.tirallis.androidnotepad.data.NotesDao
import com.tirallis.androidnotepad.data.NotesDatabase
import com.tirallis.androidnotepad.data.NotesRepositoryImpl
import com.tirallis.androidnotepad.domain.NotesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface DataModule {

    @Singleton
    @Binds
    fun bindNotesRepository(
        impl: NotesRepositoryImpl
    ): NotesRepository

    companion object {
        @Singleton
        @Provides
        fun provideDatabase(
            @ApplicationContext context: Context
        ): NotesDatabase {
            return NotesDatabase.getInstance(context)
        }

        @Singleton
        @Provides
        fun provideNotesDao(
            database: NotesDatabase
        ): NotesDao {
            return database.notesDao()
        }
    }
}