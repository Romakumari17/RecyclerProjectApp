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

    @Query("Select* from NotesEntity")
    fun getNotes() : List<NotesEntity>
    @Delete
    fun deleteNotes(notesData:NotesEntity)
    @Update
    fun updateNotes(notesData: NotesEntity)
}