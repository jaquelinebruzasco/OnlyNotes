package com.jaquelinebruzasco.onlynotes.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaquelinebruzasco.onlynotes.domain.local.OnlyNotesRepository
import com.jaquelinebruzasco.onlynotes.domain.local.TrashRepository
import com.jaquelinebruzasco.onlynotes.domain.local.model.NotesModel
import com.jaquelinebruzasco.onlynotes.domain.local.model.TrashNotesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val repository: OnlyNotesRepository,
    private val trashRepository: TrashRepository
) : ViewModel() {

    private val _note = MutableStateFlow<OnlyNotesState>(OnlyNotesState.Empty)
    val note: StateFlow<OnlyNotesState> = _note

    fun getNotes() {
        viewModelScope.launch {
            repository.getAll().collectLatest {
                if (it.isNullOrEmpty()) {
                    _note.value = OnlyNotesState.Empty
                } else {
                    _note.value = OnlyNotesState.Success(it)
                }
            }
        }
    }

    fun delete(notesModel: NotesModel) = viewModelScope.launch {
        repository.delete(notesModel)
    }

    fun insertTrash(note: NotesModel) {
        viewModelScope.launch {
            trashRepository.insert(
                TrashNotesModel(
                    id = note.id ?: 0,
                    title = note.title ?: "",
                    text = note.text ?: "",
                    category = note.category ?: ""
                )
            )
        }
    }
}

sealed class OnlyNotesState {
    object Empty : OnlyNotesState()
    object Loading : OnlyNotesState()
    class Success(val data: List<NotesModel>) : OnlyNotesState()
}