package com.jaquelinebruzasco.onlynotes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
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

        _binding.ivSave.setOnClickListener {
            if (_binding.etEnterTitle.text.toString().isBlank()) {
                Toast.makeText(requireContext(), getString(R.string.empty_title_alert), Toast.LENGTH_LONG).show()
            } else {
                saveNote()
            }
        }
    }

    private fun saveNote() {
        viewModel.insert(
            NotesModel(
                id = _binding.etEnterText.text.hashCode(),
                title = _binding.etEnterTitle.text.toString(),
                text = _binding.etEnterText.text.toString(),
                category = ""
            )
        )
        findNavController().popBackStack()
    }

}