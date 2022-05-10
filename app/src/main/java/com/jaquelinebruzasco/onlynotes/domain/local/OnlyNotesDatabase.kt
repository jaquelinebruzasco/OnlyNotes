package com.jaquelinebruzasco.onlynotes.domain.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jaquelinebruzasco.onlynotes.domain.local.model.CategoryModel
import com.jaquelinebruzasco.onlynotes.domain.local.model.NotesModel
import com.jaquelinebruzasco.onlynotes.domain.local.model.TrashNotesModel

@Database(entities = [NotesModel::class, CategoryModel::class, TrashNotesModel::class], version = 1, exportSchema = false)
abstract class OnlyNotesDatabase : RoomDatabase() {
    abstract fun onlyNotesDao(): OnlyNotesDao
    abstract fun categoryDao(): CategoryDao
    abstract fun trashDao(): TrashDao
}