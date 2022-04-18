package com.jaquelinebruzasco.onlynotes.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaquelinebruzasco.onlynotes.domain.local.CategoryRepository
import com.jaquelinebruzasco.onlynotes.domain.local.model.CategoryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryFragmentViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    fun delete(categoryModel: CategoryModel) = viewModelScope.launch {
        categoryRepository.delete(categoryModel)
    }
}