package com.jaquelinebruzasco.onlynotes.domain.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "NotesTrashModel")
data class TrashNotesModel(
    @PrimaryKey
    val id: Int,
    val title: String,
    val text: String,
    val category: String = ""
): Serializable


