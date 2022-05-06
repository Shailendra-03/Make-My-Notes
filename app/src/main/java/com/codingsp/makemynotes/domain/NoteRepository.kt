package com.codingsp.makemynotes.domain

import com.codingsp.makemynotes.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes():Flow<List<Note>>

    suspend fun getNotesById(id:Int):Note?

    suspend fun insertNote(note:Note)

    suspend fun deleteNode(note:Note)
}