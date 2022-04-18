package com.jaquelinebruzasco.onlynotes.domain.local

import androidx.room.*
import com.jaquelinebruzasco.onlynotes.domain.local.model.CategoryModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categoryModel: CategoryModel): Long

    @Query("SELECT * FROM categoryModel order by id")
    fun getAll(): Flow<List<CategoryModel>>

    @Delete
    suspend fun delete(categoryModel: CategoryModel)
}