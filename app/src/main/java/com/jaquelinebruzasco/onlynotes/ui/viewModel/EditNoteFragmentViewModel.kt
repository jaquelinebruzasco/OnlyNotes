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
class EditNoteFragmentViewModel @Inject constructor(
    private val repository: OnlyNotesRepository,
    private val trashRepository: TrashRepository
) : ViewModel() {

    fun insert(notesModel: NotesModel) {
        viewModelScope.launch {
            repository.insert(notesModel)
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

