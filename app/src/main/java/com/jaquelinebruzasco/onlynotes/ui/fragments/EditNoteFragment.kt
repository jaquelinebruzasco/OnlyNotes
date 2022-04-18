package com.jaquelinebruzasco.onlynotes.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jaquelinebruzasco.onlynotes.R
import com.jaquelinebruzasco.onlynotes.databinding.FragmentEditNoteBinding
import com.jaquelinebruzasco.onlynotes.domain.local.model.NotesModel
import com.jaquelinebruzasco.onlynotes.ui.viewModel.EditNoteFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditNoteFragment : Fragment() {

    private val viewModel by viewModels<EditNoteFragmentViewModel>()
    private lateinit var _binding: FragmentEditNoteBinding

    private val args: EditNoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadInfo(args.editNotes)

        _binding.ivSave.setOnClickListener {
            if (_binding.etEnterTitle.text.toString().isBlank()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.empty_title_alert),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                saveNote()
            }
        }

        _binding.ivDelete.setOnClickListener {
            if (args.editNotes != null) {
                args.editNotes?.let {
                    viewModel.delete(it).also {
                        Toast.makeText(requireContext(), R.string.delete_note, Toast.LENGTH_LONG)
                            .show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), R.string.discard_note, Toast.LENGTH_LONG)
                    .show()
            }
            findNavController().popBackStack()
        }
    }

    private fun loadInfo(data: NotesModel?) {
        _binding.apply {
            data?.let {
                (etEnterTitle as TextView).text = it.title
                (etEnterText as TextView).text = it.text
            }
        }
    }


    private fun saveNote() {
        viewModel.insert(
            NotesModel(
                id = args.editNotes?.id,
                title = _binding.etEnterTitle.text.toString(),
                text = _binding.etEnterText.text.toString()
            )
        )
        findNavController().popBackStack()
    }
}