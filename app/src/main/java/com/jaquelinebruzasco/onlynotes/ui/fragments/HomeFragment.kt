package com.jaquelinebruzasco.onlynotes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaquelinebruzasco.onlynotes.R
import com.jaquelinebruzasco.onlynotes.databinding.FragmentHomeBinding
import com.jaquelinebruzasco.onlynotes.domain.local.model.NotesModel
import com.jaquelinebruzasco.onlynotes.ui.fragments.adapters.NoteListAdapter
import com.jaquelinebruzasco.onlynotes.ui.viewModel.EditNoteFragmentViewModel
import com.jaquelinebruzasco.onlynotes.ui.viewModel.HomeFragmentViewModel
import com.jaquelinebruzasco.onlynotes.ui.viewModel.OnlyNotesState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeFragmentViewModel>()
    private lateinit var _binding: FragmentHomeBinding
    private val noteListAdapter by lazy { NoteListAdapter(::navigateToEditNote) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setupRecycleView()
        initObservables()
        viewModel.getNotes()
    }

    private fun initObservables() {
        lifecycleScope.launchWhenCreated {
            viewModel.note.collect { state ->
                when (state) {
                    is OnlyNotesState.Empty -> {
                        _binding.tvEmptyList.visibility = View.VISIBLE
                        noteListAdapter.list = mutableListOf()
                    }
                    is OnlyNotesState.Success -> {
                        _binding.tvEmptyList.visibility = View.GONE
                        noteListAdapter.list = state.data.toMutableList()
                    }
                }
            }
        }
    }

    private fun setupRecycleView() = with(_binding) {
        rvNoteList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = noteListAdapter
        }
        ItemTouchHelper(itemTouchHelperCallback()).attachToRecyclerView(rvNoteList)
    }

    private fun initView() {
        _binding.apply {
            actionButton.setOnClickListener {
                val navigation = HomeFragmentDirections.actionHomeFragmentToEditNoteFragment()
                findNavController().navigate(navigation)
            }
        }
    }

    private fun navigateToEditNote(data: NotesModel) {
        val navigation = HomeFragmentDirections.actionHomeFragmentToEditNoteFragment(data)
        findNavController().navigate(navigation)
    }

    private fun itemTouchHelperCallback(): ItemTouchHelper.SimpleCallback{
        return object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val note = noteListAdapter.getNoteAtPosition(viewHolder.adapterPosition)
                note?.let {
                    viewModel.delete(it).also {
                        Toast.makeText(requireContext(), R.string.delete_note, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}

