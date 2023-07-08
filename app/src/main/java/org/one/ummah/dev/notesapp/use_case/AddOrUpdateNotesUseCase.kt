package org.one.ummah.dev.notesapp.use_case

import org.one.ummah.dev.notesapp.dtos.NotesDto
import org.one.ummah.dev.notesapp.exception.InternalException
import org.one.ummah.dev.notesapp.exception.OperationTruncatedException
import org.one.ummah.dev.notesapp.exception.ValidationException
import org.one.ummah.dev.notesapp.repositories.NotesRepository

class AddOrUpdateNotesUseCase(
    private val notesRepository: NotesRepository
) {
    suspend operator fun invoke(insertOrUpdate:Boolean,notesDto: NotesDto?){
        notesDto?.let{ note ->
            if (note.title == null) {
                throw ValidationException(listOf("title"))
            }
            if(note.id == null && !insertOrUpdate) {
                throw InternalException()
            }
            notesRepository.upsertNotes(note)
        } ?: kotlin.run {
            throw InternalException()
        }

    }


}