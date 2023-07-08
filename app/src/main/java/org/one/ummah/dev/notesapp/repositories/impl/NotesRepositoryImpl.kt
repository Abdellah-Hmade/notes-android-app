package org.one.ummah.dev.notesapp.repositories.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.one.ummah.dev.notesapp.data.NotesDb
import org.one.ummah.dev.notesapp.dtos.NotesDto
import org.one.ummah.dev.notesapp.enum.SortAscOrDesc
import org.one.ummah.dev.notesapp.enum.SortByColumn
import org.one.ummah.dev.notesapp.mapper.NotesMapper
import org.one.ummah.dev.notesapp.repositories.NotesRepository

class NotesRepositoryImpl(
    private val db: NotesDb,
    private val mapper: NotesMapper
) : NotesRepository {


    override fun getNoteById(id: Int): Flow<NotesDto> {
        return db.notesDao().getNotesEntityById(id).map { notesEntity ->
            mapper.mapNoteEntityToNoteDto(notesEntity)
        }
    }

    override suspend fun upsertNotes(notesDto: NotesDto) {
        db.notesDao().upsertNotesWithTimeStamp(mapper.mapNoteDtoToNoteEntity(notesDto))
    }

    override suspend fun deleteNotes(notesDto: NotesDto) {
        db.notesDao().deleteNotesEntity(mapper.mapNoteDtoToNoteEntity(notesDto))
    }

    override fun getAllSortNotes(
        sortAscOrDesc: SortAscOrDesc,
        sortByColumn: SortByColumn
    ): Flow<List<NotesDto>> {

        return db.notesDao()
            .getAllByOrder(tableColumn = sortByColumn.code, sortAscOrDesc.code).map { notesEntity ->
                mapper.mapNotesEntitiesToNotesDtos(notesEntity)
            }

    }
}