package com.jaquelinebruzasco.onlynotes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.jaquelinebruzasco.onlynotes.databinding.FragmentCategoryBinding
import com.jaquelinebruzasco.onlynotes.domain.local.model.CategoryModel
import com.jaquelinebruzasco.onlynotes.ui.fragments.adapters.CategoryListAdapter
import com.jaquelinebruzasco.onlynotes.ui.viewModel.CategoryFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

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
    }

    private fun editCategory(data: CategoryModel) {
        EditCategoryBSDFragment(data, ::saveNewCategory).show(requireActivity().supportFragmentManager, BOTTOM_SHEET_TAG)
    }

    private fun deleteCategory(data: CategoryModel) {
        viewModel.delete(data)
    }

    private fun saveNewCategory(newCategory: CategoryModel) {
        viewModel

    }
}