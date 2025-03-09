package com.romakumari.recyclerprojectapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "tabletask" )
data class NotesEntity(@PrimaryKey(autoGenerate = true)

       @ColumnInfo(name = "TaskOwnerId")
                       var TaskOwnerId:Int =0,
       @ColumnInfo(name = "taskdescription")
       var taskdescription:String="")

