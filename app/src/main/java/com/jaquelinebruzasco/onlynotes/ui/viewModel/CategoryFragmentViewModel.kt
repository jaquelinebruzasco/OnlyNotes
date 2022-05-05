package com.jaquelinebruzasco.onlynotes.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaquelinebruzasco.onlynotes.domain.local.CategoryRepository
import com.jaquelinebruzasco.onlynotes.domain.local.model.CategoryModel
import com.jaquelinebruzasco.onlynotes.domain.local.model.NotesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryFragmentViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _category = MutableStateFlow<CategoryState>(CategoryState.Empty)
    val category: StateFlow<CategoryState> = _category

    private val _deleteCategory = MutableStateFlow<CategoryState>(CategoryState.Empty)
    val deleteCategory: StateFlow<CategoryState> = _deleteCategory

    fun insert(categoryModel: CategoryModel) {
        viewModelScope.launch {
            categoryRepository.insert(categoryModel)
        }
    }

    fun delete(category: CategoryModel) {
        viewModelScope.launch {
            categoryRepository.delete(category)
            _deleteCategory.value = CategoryState.DeleteSuccess
        }

    }

    fun getCategory() {
        viewModelScope.launch {
            categoryRepository.getAll().collectLatest {
                if (it.isNullOrEmpty()) {
                    _category.value = CategoryState.Empty
                } else {
                    _category.value = CategoryState.Success(it)
                }
            }
        }
    }
}

sealed class CategoryState {
    object Empty : CategoryState()
    class Success(val data: List<CategoryModel>) : CategoryState()
    object DeleteSuccess : CategoryState()
}