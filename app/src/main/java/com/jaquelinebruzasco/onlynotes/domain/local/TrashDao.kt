package com.jaquelinebruzasco.onlynotes.domain.local

import androidx.room.*
import com.jaquelinebruzasco.onlynotes.domain.local.model.TrashNotesModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TrashDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrashNote(notesModel: TrashNotesModel): Long

    @Query("SELECT * FROM notesTrashModel order by id")
    fun getAllTrashNotes(): Flow<List<TrashNotesModel>>

    @Delete
    suspend fun deleteTrashNote(notesModel: TrashNotesModel)
}