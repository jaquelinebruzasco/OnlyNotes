package com.jaquelinebruzasco.onlynotes.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaquelinebruzasco.onlynotes.domain.local.OnlyNotesRepository
import com.jaquelinebruzasco.onlynotes.domain.local.model.NotesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteFragmentViewModel @Inject constructor(
    private val repository: OnlyNotesRepository
) : ViewModel() {

    fun insert(notesModel: NotesModel) {
        viewModelScope.launch {
            repository.insert(notesModel)
        }
    }
}

