package com.romakumari.recyclerprojectapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "subtasktable",
    foreignKeys = [ForeignKey(
        entity = NotesEntity::class,
        parentColumns = ["TaskOwnerId"],
        childColumns = ["taskOwnerId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class SubTaskEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "subtaskId")
    val subtaskId: Int = 0,
    @ColumnInfo(name = "taskOwnerId")
    val taskOwnerId: Int,
    @ColumnInfo(name = "subtaskName")
    val subtaskName: String,
    @ColumnInfo(name = "isCompleted")
    var isCompleted: Boolean = false
)

