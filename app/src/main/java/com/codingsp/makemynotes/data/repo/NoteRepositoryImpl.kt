package com.codingsp.makemynotes.data.repo

import com.codingsp.makemynotes.data.source.NoteDao
import com.codingsp.makemynotes.domain.NoteRepository
import com.codingsp.makemynotes.model.Note
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository{
    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNotesById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun deleteNode(note: Note) {
        dao.deleteNote(note)
    }
}