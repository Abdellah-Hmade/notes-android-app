package org.one.ummah.dev.notesapp.repositories

import kotlinx.coroutines.flow.Flow
import org.one.ummah.dev.notesapp.dtos.NotesDto
import org.one.ummah.dev.notesapp.enum.SortAscOrDesc
import org.one.ummah.dev.notesapp.enum.SortByColumn

interface NotesRepository {

    suspend fun upsertNotes(notesDto: NotesDto)
    suspend fun deleteNotes(notesDto:NotesDto)
    fun getAllSortNotes(sortAscOrDesc: SortAscOrDesc, sortByColumn: SortByColumn):Flow<List<NotesDto>>
    fun getNoteById(id : Int):Flow<NotesDto>
}