package org.one.ummah.dev.notesapp.use_case

import org.one.ummah.dev.notesapp.dtos.NotesDto
import org.one.ummah.dev.notesapp.exception.InternalException
import org.one.ummah.dev.notesapp.repositories.NotesRepository

class DeleteNoteUseCase(
    private val notesRepository: NotesRepository
) {
    suspend operator fun invoke(notesDto: NotesDto){
        if(notesDto.id == null) {
            throw InternalException()
        }
        return notesRepository.deleteNotes(notesDto)
    }
}