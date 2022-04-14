package com.jaquelinebruzasco.onlynotes.domain.local

import androidx.room.*
import com.jaquelinebruzasco.onlynotes.domain.local.model.NotesModel
import kotlinx.coroutines.flow.Flow

@Dao
interface OnlyNotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notesModel: NotesModel): Long

    @Query("SELECT * FROM notesModel order by id")
    fun getAll(): Flow<List<NotesModel>>

    @Delete
    suspend fun delete(notesModel: NotesModel)
}