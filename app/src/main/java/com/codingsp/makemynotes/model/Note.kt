package com.codingsp.makemynotes.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codingsp.makemynotes.R
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Note(
    val title:String,
    val content:String,
    val timestamp: Long,
    val color:Int,
    @PrimaryKey val id:Int?=null
) : Parcelable{
    companion object{
        val noteColors= listOf(
            R.color.red_orange,
            R.color.violet,
            R.color.baby_blue,
            R.color.red_pink
        )
    }
}

class InvalidNoteException(message:String) : Exception(message)
