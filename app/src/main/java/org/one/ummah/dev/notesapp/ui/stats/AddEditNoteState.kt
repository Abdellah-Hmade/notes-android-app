package org.one.ummah.dev.notesapp.ui.stats

import org.one.ummah.dev.notesapp.dtos.NotesDto

data class AddEditNoteState(

    val selectedNoteOrCreated: NotesDto = NotesDto(),
    var addOrEditNote: Boolean = true,
){
    fun setCurrentNoteId(value:Int){
        addOrEditNote = (value < 0)
        if(!addOrEditNote){
            selectedNoteOrCreated.id = value
        }
    }

}
