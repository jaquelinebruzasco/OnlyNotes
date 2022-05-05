package com.jaquelinebruzasco.onlynotes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jaquelinebruzasco.onlynotes.R
import com.jaquelinebruzasco.onlynotes.databinding.FragmentCategoryBinding
import com.jaquelinebruzasco.onlynotes.domain.local.model.CategoryModel
import com.jaquelinebruzasco.onlynotes.domain.local.model.NotesModel
import com.jaquelinebruzasco.onlynotes.ui.fragments.adapters.CategoryListAdapter
import com.jaquelinebruzasco.onlynotes.ui.viewModel.CategoryFragmentViewModel
import com.jaquelinebruzasco.onlynotes.ui.viewModel.CategoryState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private val viewModel by viewModels<CategoryFragmentViewModel>()
    private lateinit var _binding: FragmentCategoryBinding
    private val categoryListAdapter by lazy {
        CategoryListAdapter(
            ::editCategory,
            ::deleteCategory
        )
    }

    private companion object {
        const val BOTTOM_SHEET_TAG = "bottomSheetFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservables()
        viewModel.getCategory()
    }

    private fun initObservables() {
        lifecycleScope.launchWhenCreated {
            viewModel.category.collect { state ->
                when (state) {
                    is CategoryState.Empty -> {
                        _binding.tvEmptyCategory.visibility = View.VISIBLE
                        categoryListAdapter.list = mutableListOf()
                    }
                    is CategoryState.Success -> {
                        _binding.tvEmptyCategory.visibility = View.GONE
                        categoryListAdapter.list = state.data.toMutableList()
                    }
                    else -> Unit
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.deleteCategory.collectLatest {
                if (it is CategoryState.DeleteSuccess) {
                    viewModel.getCategory()
                }
            }
        }
    }

    private fun setupRecycleView() = with(_binding) {
        rvCategoryList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = categoryListAdapter
        }
    }

    private fun initView() {
        setupRecycleView()
        _binding.apply {
            buttonAdd.setOnClickListener {
                if (_binding.etEnterCategory.text.toString().isBlank()) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.empty_category_alert),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    viewModel.insert(
                        CategoryModel(
                            id = null,
                            name = _binding.etEnterCategory.text.toString()
                        )
                    ).also {
                        _binding.etEnterCategory.setText("")
                    }
                }
            }
        }
    }

    private fun editCategory(data: CategoryModel) {
            val dialog = EditCategoryBSDFragment(
            data,
            ::saveNewCategory
        )
        dialog.isCancelable = false
        dialog.show(requireActivity().supportFragmentManager, BOTTOM_SHEET_TAG)
    }

    private fun deleteCategory(data: CategoryModel) {
        viewModel.delete(data)
    }

    private fun saveNewCategory(newCategory: CategoryModel) {
        viewModel.insert(newCategory)
    }
}