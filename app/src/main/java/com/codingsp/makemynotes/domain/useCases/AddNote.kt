package com.codingsp.makemynotes.domain.useCases

import com.codingsp.makemynotes.domain.NoteRepository
import com.codingsp.makemynotes.model.InvalidNoteException
import com.codingsp.makemynotes.model.Note

class AddNote(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note:Note){
        if(note.title.isBlank()){
            throw InvalidNoteException("Please Enter a Title")
        }
        if(note.content.isBlank()){
            throw InvalidNoteException("Please Enter Some Content")
        }
        repository.insertNote(note)
    }
}