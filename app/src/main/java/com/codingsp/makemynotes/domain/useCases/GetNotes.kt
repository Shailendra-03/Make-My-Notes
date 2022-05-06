package com.codingsp.makemynotes.domain.useCases

import com.codingsp.makemynotes.domain.NoteRepository
import com.codingsp.makemynotes.domain.util.NoteOrder
import com.codingsp.makemynotes.domain.util.OrderType
import com.codingsp.makemynotes.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(
    private val repository: NoteRepository
) {

    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ):Flow<List<Note>>{
        return repository.getNotes().map { noteList->
            when(noteOrder.orderType){
                is OrderType.Ascending->{
                    when(noteOrder){
                        is NoteOrder.Title->noteList.sortedBy { it.title.lowercase() }
                        is NoteOrder.Date-> noteList.sortedBy { it.timestamp }
                        is NoteOrder.Color-> noteList.sortedBy { it.color }
                    }
                }
                is OrderType.Descending->{
                    when(noteOrder){
                        is NoteOrder.Title->noteList.sortedByDescending { it.title.lowercase() }
                        is NoteOrder.Date-> noteList.sortedByDescending { it.timestamp }
                        is NoteOrder.Color-> noteList.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}