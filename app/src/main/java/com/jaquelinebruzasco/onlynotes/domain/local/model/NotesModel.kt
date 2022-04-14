package com.jaquelinebruzasco.onlynotes.domain.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "NotesModel")
data class NotesModel(
    @PrimaryKey
    val id: Int,
    val title: String?,
    val text: String?,
    val category: String?
): Serializable
