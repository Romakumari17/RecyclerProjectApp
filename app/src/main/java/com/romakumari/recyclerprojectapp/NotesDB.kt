package com.romakumari.recyclerprojectapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(version = 1, exportSchema = true, entities = [NotesEntity::class])
abstract class NotesDB:RoomDatabase() {
    abstract fun notesdao(): NotesDao
    companion object {
        var notesdb: NotesDB? = null
        fun getDatabase(context: Context): NotesDB {
            if (notesdb == null) {
                notesdb = Room.databaseBuilder(
                    context,
                    NotesDB::class.java, "NotesDB"
                ).allowMainThreadQueries()
                    .build()
            }
            return notesdb!!
        }
    }
}