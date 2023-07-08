package org.one.ummah.dev.notesapp.ui.events

import androidx.compose.ui.focus.FocusState

sealed class AddEditNotesEvents{
    object UpsertNotesEvent:AddEditNotesEvents()
    data class GetNoteByIdEvent(val noteId:Int):AddEditNotesEvents()
    data class EnteredTitle(val value: String?): AddEditNotesEvents()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditNotesEvents()
    data class EnteredContent(val value: String?): AddEditNotesEvents()
    data class ChangeContentFocus(val focusState: FocusState): AddEditNotesEvents()
    data class ChangeColor(val color: String?): AddEditNotesEvents()
}
