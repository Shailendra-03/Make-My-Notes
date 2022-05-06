package com.codingsp.makemynotes

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.codingsp.makemynotes.databinding.ItemNoteBinding
import com.codingsp.makemynotes.domain.util.Constants
import com.codingsp.makemynotes.model.Note

class DisplayNotesAdapter(
    private val fragment:Fragment,private val listener: DisplayNotesAdapterInterface
): RecyclerView.Adapter<DisplayNotesAdapter.MyViewHolder>() {
    private var noteList:List<Note> = emptyList()

    inner class MyViewHolder(item:ItemNoteBinding):RecyclerView.ViewHolder(item.root) {
        val tvTitle=item.tvTitle
        val tvContent=item.tvContent
        var rvItem =item.cvItem
        val ivDelete=item.ivDelete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding=ItemNoteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvTitle.text=noteList[position].title
        holder.tvContent.text=noteList[position].content
        holder.rvItem.setCardBackgroundColor(ContextCompat.getColor(fragment.requireContext(),Note.noteColors[noteList[position].color]))

        holder.itemView.setOnClickListener {
            listener.onItemClicked(noteList[position])
        }
        holder.ivDelete.setOnClickListener{
            listener.deleteNote(noteList[position])
        }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun getList(list:List<Note>){
        noteList=list
        notifyDataSetChanged()
    }
}

interface DisplayNotesAdapterInterface{
    fun deleteNote(note: Note)
    fun onItemClicked(note: Note)
}