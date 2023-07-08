package org.one.ummah.dev.notesapp.ui.stats

sealed class Screen(val route: String) {
    object HomeScreen : Screen("notes_screen")
    object AddOrEditNoteScreen : Screen("add_edit_note_screen")
}
