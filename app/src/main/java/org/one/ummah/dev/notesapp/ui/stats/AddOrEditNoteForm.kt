package org.one.ummah.dev.notesapp.ui.stats

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.one.ummah.dev.notesapp.utils.GlobalConstants

data class AddOrEditNoteForm(
    val titleTextFiled: NoteTextFieldState = NoteTextFieldState(
        hint = TITLE_HINT),
    val contentTextField:NoteTextFieldState = NoteTextFieldState(
        hint = CONTENT_HINT
    ),
    private val _colorRadioButtonField: MutableState<String> = mutableStateOf(GlobalConstants.COLORS.random()),
    var colorRadioButtonField: State<String> = _colorRadioButtonField
){
    companion object{
        val TITLE_HINT = "give a subject to your note ..."
        val CONTENT_HINT = "create a memory now ..."
    }

    fun setColorsValue(value:String?){
        value?.let {
            _colorRadioButtonField.value = value
        }?: kotlin.run {
            _colorRadioButtonField.value = GlobalConstants.COLORS.random()
        }

    }
}