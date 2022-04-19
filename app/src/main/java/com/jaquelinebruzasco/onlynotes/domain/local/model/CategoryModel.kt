package com.jaquelinebruzasco.onlynotes.domain.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CategoryModel")
data class CategoryModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var name: String
)