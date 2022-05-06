package com.codingsp.makemynotes.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.codingsp.makemynotes.DisplayNotesAdapter
import com.codingsp.makemynotes.DisplayNotesAdapterInterface
import com.codingsp.makemynotes.R
import com.codingsp.makemynotes.databinding.FragmentDisplayNotesBinding
import com.codingsp.makemynotes.domain.util.Constants
import com.codingsp.makemynotes.domain.util.NoteOrder
import com.codingsp.makemynotes.domain.util.OrderType
import com.codingsp.makemynotes.model.Note
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class DisplayNotesFragment : Fragment(),View.OnClickListener, DisplayNotesAdapterInterface {

    private lateinit var binding: FragmentDisplayNotesBinding
    private lateinit var adapter:DisplayNotesAdapter
    private lateinit var orderType: OrderType
    private var noteOrderId by Delegates.notNull<Int>()

    private  val viewModel: DisplayNotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        orderType=OrderType.Descending
        noteOrderId=0
        binding= FragmentDisplayNotesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAddNote.setOnClickListener(this)
        adapter=DisplayNotesAdapter(this,this)
        binding.rvNotes.layoutManager=GridLayoutManager(requireContext(),2)
        binding.rvNotes.adapter=adapter

        viewModel.noteList.observe(viewLifecycleOwner){
            adapter.getList(it)
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.note_order_list,
            android.R.layout.simple_spinner_item
        ).also { adapter->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spNoteOrder.adapter=adapter
        }

        binding.spNoteOrder.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                noteOrderId=pos
                getNoteList(noteOrderId)
            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) { }
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.notes_order_type,
            android.R.layout.simple_spinner_item
        ).also { adapter->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spOrderType.adapter=adapter
        }

        binding.spOrderType.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                when(pos){
                    0-> orderType=OrderType.Descending
                    1-> orderType=OrderType.Ascending
                }
                getNoteList(noteOrderId)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) { }
        }
    }

    override fun onClick(view: View?) {
        when(view){
            binding.fabAddNote->{
                findNavController().navigate(R.id.action_displayNotesFragment_to_addNotesFragment)
            }
        }
    }

    private fun getNoteList(noteOrderId :Int) {
        when(noteOrderId){
            0-> viewModel.getNoteList(NoteOrder.Date(orderType))
            1-> viewModel.getNoteList(NoteOrder.Title(orderType))
            2-> viewModel.getNoteList(NoteOrder.Color(orderType))
        }
    }

    override fun deleteNote(note: Note) {
        viewModel.deleteNote(note)
        Snackbar.make(binding.root,"Note Deleted",Snackbar.LENGTH_LONG)
            .setAction("Undo"){ viewModel.restoreNote() }
            .show()
    }

    override fun onItemClicked(note: Note) {
        val bundle=Bundle()
        bundle.putParcelable(Constants.NOTE_FROM_DISPLAY_NOTES_FRAGMENT_TO_ADD_NOTES_FRAGMENT,note)
        findNavController().navigate(R.id.action_displayNotesFragment_to_addNotesFragment,bundle)
    }
}