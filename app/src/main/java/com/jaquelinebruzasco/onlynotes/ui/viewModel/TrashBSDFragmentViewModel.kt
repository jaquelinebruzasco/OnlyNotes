package com.jaquelinebruzasco.onlynotes.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaquelinebruzasco.onlynotes.domain.local.OnlyNotesRepository
import com.jaquelinebruzasco.onlynotes.domain.local.TrashRepository
import com.jaquelinebruzasco.onlynotes.domain.local.model.NotesModel
import com.jaquelinebruzasco.onlynotes.domain.local.model.TrashNotesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrashBSDFragmentViewModel @Inject constructor(
    private val repository: OnlyNotesRepository,
    private val trashRepository: TrashRepository
) : ViewModel() {

    fun permanentlyDelete(note: TrashNotesModel) = viewModelScope.launch {
        trashRepository.delete(note)
    }

    fun restoreNote(note: TrashNotesModel) {
        viewModelScope.launch {
            trashRepository.delete(note)
            repository.insert(
                NotesModel(
                    id = note.id,
                    title = note.title,
                    text = note.text,
                    category = note.category
                )
            )
        }
    }
}