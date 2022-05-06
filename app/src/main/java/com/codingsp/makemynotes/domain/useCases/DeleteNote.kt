package com.codingsp.makemynotes.domain.useCases

import com.codingsp.makemynotes.domain.NoteRepository
import com.codingsp.makemynotes.model.Note

class DeleteNote(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note:Note){
        repository.deleteNode(note)
    }
}