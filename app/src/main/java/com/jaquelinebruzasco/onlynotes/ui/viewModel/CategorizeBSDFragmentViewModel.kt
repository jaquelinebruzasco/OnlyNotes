package com.jaquelinebruzasco.onlynotes.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaquelinebruzasco.onlynotes.domain.local.CategoryRepository
import com.jaquelinebruzasco.onlynotes.domain.local.OnlyNotesRepository
import com.jaquelinebruzasco.onlynotes.domain.local.model.CategoryModel
import com.jaquelinebruzasco.onlynotes.domain.local.model.NotesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategorizeBSDFragmentViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val repository: OnlyNotesRepository
): ViewModel() {

    private val _category = MutableStateFlow<SelectCategoryState>(SelectCategoryState.Empty)
    val category: StateFlow<SelectCategoryState> = _category

    private val _insertCategory = MutableStateFlow<InsertCategoryState>(InsertCategoryState.Empty)
    val insertCategory: StateFlow<InsertCategoryState> = _insertCategory

    fun getCategory() {
        viewModelScope.launch {
            categoryRepository.getAll().collectLatest {
                if (it.isNullOrEmpty()) {
                    _category.value = SelectCategoryState.Empty
                } else {
                    _category.value = SelectCategoryState.Success(it)
                }
            }
        }
    }

    fun setCategory(note: NotesModel, newCategory: CategoryModel) {
        val newNote = NotesModel(note.id, note.title, note.text, newCategory.name)
        viewModelScope.launch {
            repository.insert(newNote)
            _insertCategory.value = InsertCategoryState.Success(newNote)
        }
    }
}

sealed class SelectCategoryState {
    object Empty : SelectCategoryState()
    class Success(val data: List<CategoryModel>) : SelectCategoryState()
}

sealed class InsertCategoryState {
    object Empty : InsertCategoryState()
    class Success(val noteData: NotesModel) : InsertCategoryState()
}