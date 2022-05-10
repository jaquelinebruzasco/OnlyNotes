package com.jaquelinebruzasco.onlynotes.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaquelinebruzasco.onlynotes.domain.local.TrashRepository
import com.jaquelinebruzasco.onlynotes.domain.local.model.TrashNotesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrashFragmentViewModel @Inject constructor(
    private val trashRepository: TrashRepository
) : ViewModel() {

    private val _trashNote = MutableStateFlow<TrashNotesState>(TrashNotesState.Empty)
    val trashNote: StateFlow<TrashNotesState> = _trashNote

    fun getTrashNotes() {
        viewModelScope.launch {
            trashRepository.getAll().collectLatest {
                if (it.isNullOrEmpty()) {
                    _trashNote.value = TrashNotesState.Empty
                } else {
                    _trashNote.value = TrashNotesState.Success(it)
                }
            }
        }
    }
}

sealed class TrashNotesState {
    object Empty : TrashNotesState()
    class Success(val data: List<TrashNotesModel>) : TrashNotesState()
}