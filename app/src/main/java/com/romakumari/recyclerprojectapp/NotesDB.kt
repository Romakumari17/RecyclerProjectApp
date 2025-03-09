package com.romakumari.recyclerprojectapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(version = 2, exportSchema = true, entities = [SubTaskEntity::class ,NotesEntity::class])
abstract class NotesDB:RoomDatabase() {
    abstract fun notesdao(): NotesDao

    companion object {
        var notesdb: NotesDB? = null
        fun getDatabase(context: Context): NotesDB {
            if (notesdb == null) {
                notesdb = Room.databaseBuilder(
                    context,
                    NotesDB::class.java, "NotesDB"
                ).addMigrations(MIGRATION_1_2)
                    .allowMainThreadQueries()
                    .build()
            }
            return notesdb!!
        }


    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                """
            CREATE TABLE IF NOT EXISTS subtasktable (
                subtaskid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                TaskOwnerId INTEGER NOT NULL,
                subtaskName TEXT,
                FOREIGN KEY (TaskOwnerId) REFERENCES tabletask(TaskOwnerId) ON DELETE CASCADE
            )
            """
            )
        }
    }

}
}
