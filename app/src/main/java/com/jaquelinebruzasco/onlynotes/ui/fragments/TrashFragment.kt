package com.jaquelinebruzasco.onlynotes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jaquelinebruzasco.onlynotes.databinding.FragmentTrashBinding
import com.jaquelinebruzasco.onlynotes.domain.local.model.TrashNotesModel
import com.jaquelinebruzasco.onlynotes.ui.fragments.adapters.TrashNoteListAdapter
import com.jaquelinebruzasco.onlynotes.ui.viewModel.TrashBSDFragmentViewModel
import com.jaquelinebruzasco.onlynotes.ui.viewModel.TrashFragmentViewModel
import com.jaquelinebruzasco.onlynotes.ui.viewModel.TrashNotesState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class TrashFragment : Fragment() {

    private val viewModel by viewModels<TrashFragmentViewModel>()
    private lateinit var _binding: FragmentTrashBinding
    private val trashNoteListAdapter by lazy { TrashNoteListAdapter(::openBottomSheetDialog) }

    private companion object {
        const val BOTTOM_SHEET_TAG = "trashBottomSheetFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrashBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        initObservables()
        viewModel.getTrashNotes()
    }

    private fun initObservables() {
        lifecycleScope.launchWhenCreated {
            viewModel.trashNote.collect { state ->
                when(state) {
                    is TrashNotesState.Empty -> {
                        _binding.tvEmptyTrash.visibility = View.VISIBLE
                        trashNoteListAdapter.list = mutableListOf()
                    }
                    is TrashNotesState.Success -> {
                        _binding.tvEmptyTrash.visibility = View.GONE
                        trashNoteListAdapter.list = state.data.toMutableList()
                    }
                }
            }
        }
    }

    private fun setupRecycleView() = with(_binding) {
        rvNoteList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = trashNoteListAdapter
        }
    }

    private fun openBottomSheetDialog(note: TrashNotesModel) {
        val dialog = TrashBSDFragment(note)
        dialog.show(requireActivity().supportFragmentManager, BOTTOM_SHEET_TAG)
    }
}