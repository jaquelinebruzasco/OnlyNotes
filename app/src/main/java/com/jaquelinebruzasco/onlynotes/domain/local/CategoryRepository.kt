package com.jaquelinebruzasco.onlynotes.domain.local

import com.jaquelinebruzasco.onlynotes.domain.local.model.CategoryModel
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val dao: CategoryDao
) {
    suspend fun insert(categoryModel: CategoryModel) = dao.insert(categoryModel)
    fun getAll() = dao.getAll()
    suspend fun delete(categoryModel: CategoryModel) = dao.delete(categoryModel)
}