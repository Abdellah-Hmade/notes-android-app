package org.one.ummah.dev.notesapp.mapper

import org.mapstruct.Mapper
import org.one.ummah.dev.notesapp.data.domain.NotesEntity
import org.one.ummah.dev.notesapp.dtos.NotesDto

@Mapper
interface NotesMapper {

    fun mapNoteEntityToNoteDto(notesDto: NotesEntity): NotesDto
    fun mapNotesEntitiesToNotesDtos(notesEntities: List<NotesEntity>): List<NotesDto>

    fun mapNoteDtoToNoteEntity(notesDto: NotesDto): NotesEntity
    fun mapNotesDtosToNotesEntities(notesDtos: List<NotesDto>): List<NotesEntity>
}