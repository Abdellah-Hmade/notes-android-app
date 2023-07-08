package org.one.ummah.dev.notesapp.data.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("notes")
data class NotesEntity(
    @PrimaryKey(autoGenerate = true) var id:Int,
    @ColumnInfo("content") var content:String?,
    @ColumnInfo("title") var title:String?,
    @ColumnInfo("date") var date:Long?,
    @ColumnInfo("color") var color:String?
)