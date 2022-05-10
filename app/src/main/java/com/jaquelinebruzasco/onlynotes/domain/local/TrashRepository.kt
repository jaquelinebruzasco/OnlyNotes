package com.jaquelinebruzasco.onlynotes.domain.local

import com.jaquelinebruzasco.onlynotes.domain.local.model.TrashNotesModel
import javax.inject.Inject

class TrashRepository @Inject constructor(
    private val dao: TrashDao
) {
    suspend fun insert(notesModel: TrashNotesModel) = dao.insertTrashNote(notesModel)
    fun getAll() = dao.getAllTrashNotes()
    suspend fun delete(notesModel: TrashNotesModel) = dao.deleteTrashNote(notesModel)
}