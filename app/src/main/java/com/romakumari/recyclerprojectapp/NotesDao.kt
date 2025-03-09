package com.romakumari.recyclerprojectapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
@Dao
interface NotesDao {
    @Insert
    fun  insertNotes(notesData: NotesEntity)

    @Query("Select* from tabletask ")
    fun getAlltask() : List<NotesEntity>
    @Delete
    fun deleteNotes(notesData:NotesEntity)
    @Update
    fun updateNotes(notesData: NotesEntity)
    @Insert
    fun insertSub(subTaskEntity: SubTaskEntity)
    @Query("SELECT * FROM subtasktable where taskOwnerId = :taskOwnerId")
    fun getSub(taskOwnerId : Int): List<SubTaskEntity>
    @Update
    fun updateSub(subTaskEntity: SubTaskEntity)
    @Delete
    fun deleteSub(subTaskEntity: SubTaskEntity)


}