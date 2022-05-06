package com.codingsp.makemynotes.domain.useCases

data class NoteUseCases(
    val getNotes: GetNotes,
    val deleteNote: DeleteNote,
    val addNote:AddNote
)
