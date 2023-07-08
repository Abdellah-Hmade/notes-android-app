package org.one.ummah.dev.notesapp.ui.stats

import org.one.ummah.dev.notesapp.dtos.NotesDto
import org.one.ummah.dev.notesapp.enum.SortAscOrDesc
import org.one.ummah.dev.notesapp.enum.SortByColumn

data class NoteState(
    val notesOrdered: List<NotesDto> = emptyList(),
    val recentlyDeletedNote: NotesDto? = null,
    val sortByColumn: SortByColumn = SortByColumn.DATE,
    val sortAscOrDesc: SortAscOrDesc = SortAscOrDesc.DESC,
    var isOrderSectionVisible: Boolean = false
)
