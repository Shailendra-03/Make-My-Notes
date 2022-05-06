package com.codingsp.makemynotes.fragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingsp.makemynotes.domain.useCases.NoteUseCases
import com.codingsp.makemynotes.model.InvalidNoteException
import com.codingsp.makemynotes.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private var _errorMessage= MutableSharedFlow<String>()
    val errorMessage= _errorMessage.asSharedFlow()

    private var _savedOrNot = MutableSharedFlow<Boolean>()
    val savedOrNot= _savedOrNot.asSharedFlow()

    fun saveNote(note:Note){
        viewModelScope.launch {
            try {
                noteUseCases.addNote(note)
                _savedOrNot.emit(value = true)
            }catch (e:InvalidNoteException){
                _errorMessage.emit(e.message?:"Failed to Save Note")
            }
        }
    }
}