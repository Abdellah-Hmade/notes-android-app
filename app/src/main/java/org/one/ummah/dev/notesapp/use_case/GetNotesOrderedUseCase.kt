package org.one.ummah.dev.notesapp.use_case

import kotlinx.coroutines.flow.Flow
import org.one.ummah.dev.notesapp.dtos.NotesDto
import org.one.ummah.dev.notesapp.enum.SortAscOrDesc
import org.one.ummah.dev.notesapp.enum.SortByColumn
import org.one.ummah.dev.notesapp.repositories.NotesRepository

class GetNotesOrderedUseCase(
    private val notesRepository: NotesRepository
) {
    var varSortByColumn: SortByColumn = SortByColumn.DEFAULT_SORT
    var varSortAscOrDesc: SortAscOrDesc = SortAscOrDesc.DEFAULT_SORT

    operator fun invoke(sortAscOrDesc: SortAscOrDesc?,sortByColumn: SortByColumn?): Flow<List<NotesDto>> {
        sortByColumn?.let {
            varSortByColumn = it
        }
        sortAscOrDesc?.let {
            varSortAscOrDesc = it
        }
        return notesRepository
            .getAllSortNotes( varSortAscOrDesc, varSortByColumn).also {

                varSortByColumn = SortByColumn.DATE
                varSortAscOrDesc = SortAscOrDesc.DESC
            }

    }
}