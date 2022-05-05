package com.jaquelinebruzasco.onlynotes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jaquelinebruzasco.onlynotes.databinding.FragmentBottomSheetDialogCategorizeBinding
import com.jaquelinebruzasco.onlynotes.domain.local.model.CategoryModel
import com.jaquelinebruzasco.onlynotes.domain.local.model.NotesModel
import com.jaquelinebruzasco.onlynotes.ui.fragments.adapters.CategorizeListAdapter
import com.jaquelinebruzasco.onlynotes.ui.viewModel.CategorizeBSDFragmentViewModel
import com.jaquelinebruzasco.onlynotes.ui.viewModel.InsertCategoryState
import com.jaquelinebruzasco.onlynotes.ui.viewModel.SelectCategoryState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CategorizeBSDFragment(
    private val note: NotesModel,
    private val save: (NotesModel) -> Unit
) : BottomSheetDialogFragment() {

    private val viewModel by viewModels<CategorizeBSDFragmentViewModel>()
    private lateinit var _binding: FragmentBottomSheetDialogCategorizeBinding
    private val categorizeListAdapter by lazy { CategorizeListAdapter(::categorizeNote) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetDialogCategorizeBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setupRecycleView()
        initObservables()
        viewModel.getCategory()
    }

    private fun initView() {
        _binding.ivCancel.setOnClickListener { this@CategorizeBSDFragment.dismiss() }
    }

    private fun setupRecycleView() = with(_binding) {
        rvCategorizeList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = categorizeListAdapter
        }
    }

    private fun initObservables() {
        lifecycleScope.launchWhenCreated {
            viewModel.category.collect { state ->
                when (state) {
                    is SelectCategoryState.Empty -> {
                        _binding.tvEmptyCategoryList.visibility = View.VISIBLE
                        categorizeListAdapter.list = mutableListOf()
                    }
                    is SelectCategoryState.Success -> {
                        _binding.tvEmptyCategoryList.visibility = View.GONE
                        categorizeListAdapter.list = state.data.toMutableList()
                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.insertCategory.collect { state ->
                when(state) {
                    is InsertCategoryState.Success -> {
                        save.invoke(state.noteData)
                        this@CategorizeBSDFragment.dismiss()
                    }
                }
            }
        }
    }

    private fun categorizeNote(data: CategoryModel) {
        viewModel.setCategory(note, data)
    }
}