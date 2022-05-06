package com.codingsp.makemynotes.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.codingsp.makemynotes.adapters.ColorsSpinnerAdapter
import com.codingsp.makemynotes.databinding.FragmentAddNotesBinding
import com.codingsp.makemynotes.domain.util.Constants
import com.codingsp.makemynotes.model.Colors
import com.codingsp.makemynotes.model.Note
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AddNotesFragment : Fragment(),View.OnClickListener {

    private lateinit var binding:FragmentAddNotesBinding
    private var noteId:Int?=null
    private var noteColor:Int?=null

    private val viewModel: AddNotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        noteColor= (0 until Note.noteColors.size).random()
        binding= FragmentAddNotesBinding.inflate(inflater,container,false)
        val bundle=this.arguments
        bundle?.let {
            val note=it.getParcelable(Constants.NOTE_FROM_DISPLAY_NOTES_FRAGMENT_TO_ADD_NOTES_FRAGMENT) as Note?
            note?.let { itr->
                noteColor=itr.color
                noteId=itr.id
                binding.etTitle.setText(itr.title)
                binding.etContent.setText(itr.content)
            }
        }
        setUpCustomerSpinner()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ibSave.setOnClickListener(this)

        lifecycleScope.launchWhenStarted {
            viewModel.errorMessage.collectLatest {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.savedOrNot.collectLatest {
                if(it) requireActivity().onBackPressed()
            }
        }
    }

    private fun setUpCustomerSpinner() {
        val adapter= ColorsSpinnerAdapter(requireContext(),Colors.getColors())
        binding.spColors.adapter=adapter
        binding.spColors.setSelection(noteColor!!)

        binding.spColors.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                noteColor=pos
                binding.llAddNotesFragment.setBackgroundColor(ContextCompat.getColor(requireContext(),Note.noteColors[noteColor!!]))
            }
            override fun onNothingSelected(p0: AdapterView<*>?){ }
        }
    }

    override fun onClick(view: View?) {
        when(view){
            binding.ibSave->{
                val note=Note(
                    binding.etTitle.text.toString(),
                    binding.etContent.text.toString(),
                    System.currentTimeMillis(),
                    noteColor!!,
                    noteId
                )
                viewModel.saveNote(note)
            }
        }
    }
}