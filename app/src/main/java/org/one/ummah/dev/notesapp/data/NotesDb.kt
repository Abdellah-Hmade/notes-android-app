package org.one.ummah.dev.notesapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import org.one.ummah.dev.notesapp.data.dao.NotesEntityDao
import org.one.ummah.dev.notesapp.data.domain.NotesEntity

@Database(entities = [NotesEntity::class], version = 1)
abstract class NotesDb : RoomDatabase() {
    companion object {
        val DATABASE_NAME:String = "notes-database"
    }
    abstract fun notesDao():NotesEntityDao

}
