package org.one.ummah.dev.notesapp.ui.events

import org.one.ummah.dev.notesapp.dtos.NotesDto
import org.one.ummah.dev.notesapp.enum.SortAscOrDesc
import org.one.ummah.dev.notesapp.enum.SortByColumn

sealed class HomeNotesEvents {
    data class OrderNotesEvent(
        val sortByColumn: SortByColumn = SortByColumn.DATE,
        val sortAscOrDesc: SortAscOrDesc = SortAscOrDesc.DESC
    ) : HomeNotesEvents()

    data class DeleteNoteEvent(val notesDto: NotesDto) : HomeNotesEvents()
    object RestoreNote : HomeNotesEvents()
    object PauseEvent : HomeNotesEvents()
    object ResumeEvent : HomeNotesEvents()
    object ToggleOrderSection : HomeNotesEvents()
}