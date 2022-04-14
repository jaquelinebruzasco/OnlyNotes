package com.jaquelinebruzasco.onlynotes.domain.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jaquelinebruzasco.onlynotes.domain.local.model.NotesModel

@Database(entities = [NotesModel::class], version = 1, exportSchema = false)
abstract class OnlyNotesDatabase : RoomDatabase() {
    abstract fun onlyNotesDao(): OnlyNotesDao
}