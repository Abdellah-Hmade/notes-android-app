package org.one.ummah.dev.notesapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import org.one.ummah.dev.notesapp.data.domain.NotesEntity

@Dao
abstract class NotesEntityDao {

    @Query("Select * from notes")
    abstract fun getAll(): Flow<List<NotesEntity>>

    @Query("Select * from notes where id = :id")
    abstract fun getNotesEntityById(id: Int): Flow<NotesEntity>

    @Query(
        "Select * from notes order by " +
                "case when :tableColumn = 0 And :ascOrDesc = 0 then title End DESC, " +
                "case when :tableColumn = 0 And :ascOrDesc = 1 then title END ASC," +
                "case when :tableColumn = 1 And :ascOrDesc = 0 then date  End DESC," +
                "case when :tableColumn = 1 And :ascOrDesc = 1 then date  END ASC," +
                "case when :tableColumn = 2 And :ascOrDesc = 0 then color End DESC," +
                "case when :tableColumn = 2 And :ascOrDesc = 1 then color END ASC"
    )
    abstract fun getAllByOrder(tableColumn: Int, ascOrDesc: Int): Flow<List<NotesEntity>>

    @Upsert
    abstract suspend fun upsertNotesEntity(notesEntity: NotesEntity)

    @Delete
    abstract suspend fun deleteNotesEntity(notesEntity: NotesEntity)

    suspend fun upsertNotesWithTimeStamp(notesEntity: NotesEntity) {
        upsertNotesEntity(notesEntity.apply {
            notesEntity.date = System.currentTimeMillis()
        })
    }
}