package com.codingsp.makemynotes.fragment

import androidx.lifecycle.*
import com.codingsp.makemynotes.domain.useCases.NoteUseCases
import com.codingsp.makemynotes.domain.util.NoteOrder
import com.codingsp.makemynotes.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisplayNotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private var _noteList = MutableLiveData<List<Note>>()
    val noteList : LiveData<List<Note>> get() = _noteList

    private var lastDeletedNote:Note?=null

    private var getNotesJob:Job?=null


    fun getNoteList(noteOrder: NoteOrder){
        getNotesJob?.cancel()
        getNotesJob=noteUseCases.getNotes(noteOrder).onEach {
            _noteList.value= it
        }.launchIn(viewModelScope)
    }

    fun deleteNote(note: Note){
        viewModelScope.launch {
            noteUseCases.deleteNote(note)
            lastDeletedNote=note
        }
    }

    fun restoreNote(){
        viewModelScope.launch {
            lastDeletedNote?.let {
                noteUseCases.addNote(it)
            }
            lastDeletedNote=null
        }
    }

}