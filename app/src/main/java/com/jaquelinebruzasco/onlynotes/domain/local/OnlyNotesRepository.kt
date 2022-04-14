package com.jaquelinebruzasco.onlynotes.domain.local

import com.jaquelinebruzasco.onlynotes.domain.local.model.NotesModel
import javax.inject.Inject

class OnlyNotesRepository @Inject constructor(
    private val dao: OnlyNotesDao
) {

    suspend fun insert(notesModel: NotesModel) = dao.insert(notesModel)
    fun getAll() = dao.getAll()
    suspend fun delete(notesModel: NotesModel) = dao.delete(notesModel)
}