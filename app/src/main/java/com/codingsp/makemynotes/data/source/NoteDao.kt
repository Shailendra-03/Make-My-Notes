package com.codingsp.makemynotes.data.source

import androidx.room.*
import com.codingsp.makemynotes.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("Select * from note")
    fun getNotes():Flow<List<Note>>

    @Query("Select * from note where id=:id")
    suspend fun getNoteById(id:Int):Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note:Note)

    @Delete
    suspend fun deleteNote(note: Note)

}