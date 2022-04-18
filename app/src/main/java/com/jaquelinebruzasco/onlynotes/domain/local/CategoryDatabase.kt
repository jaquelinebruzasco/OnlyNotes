package com.jaquelinebruzasco.onlynotes.domain.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jaquelinebruzasco.onlynotes.domain.local.model.CategoryModel

@Database(entities = [CategoryModel::class], version = 1, exportSchema = false)
abstract class CategoryDatabase : RoomDatabase() {
    abstract fun categoryDao() : CategoryDao
}