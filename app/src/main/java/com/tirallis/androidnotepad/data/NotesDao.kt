package com.tirallis.androidnotepad.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface NotesDao {

    @Query("SELECT * FROM notes ORDER BY updatedAt DESC")
    fun getAllNotes(): Flow<List<NoteDBModel>>

    @Query("SELECT * FROM notes WHERE id == :noteId ORDER BY updatedAt DESC")
    suspend fun getNote(noteId: Int): NoteDBModel

    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%' ORDER BY updatedAt DESC")
    fun searchNotes(query: String): Flow<List<NoteDBModel>>

    @Query("DELETE FROM notes WHERE id == :noteId")
    suspend fun deleteNote(noteId: Int)

    @Query("UPDATE notes SET isPinned = NOT isPinned WHERE id == :noteId")
    suspend fun switchPinnedStatus(noteId: Int)

    @Upsert
    suspend fun addNote(note: NoteDBModel)
}