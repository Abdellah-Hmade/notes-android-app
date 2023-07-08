package org.one.ummah.dev.notesapp.use_case

class NotesUseCase(
    val getNotesOrdered :GetNotesOrderedUseCase,
    val deleteNote:DeleteNoteUseCase,
    val upsertNote:AddOrUpdateNotesUseCase,
    val getNote:GetNoteUseCase
)