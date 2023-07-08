package org.one.ummah.dev.notesapp.use_case

import kotlinx.coroutines.flow.Flow
import org.one.ummah.dev.notesapp.dtos.NotesDto
import org.one.ummah.dev.notesapp.repositories.NotesRepository

class GetNoteUseCase(
    private val notesRepository: NotesRepository
){
    operator fun invoke(id:Int): Flow<NotesDto> {
        return notesRepository.getNoteById(id)
    }
}