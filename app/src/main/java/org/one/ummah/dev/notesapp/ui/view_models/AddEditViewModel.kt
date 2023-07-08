package org.one.ummah.dev.notesapp.ui.view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.one.ummah.dev.notesapp.exception.CustomException
import org.one.ummah.dev.notesapp.ui.events.AddEditNotesEvents
import org.one.ummah.dev.notesapp.ui.stats.AddEditNoteState
import org.one.ummah.dev.notesapp.ui.stats.AddOrEditNoteForm
import org.one.ummah.dev.notesapp.use_case.NotesUseCase
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val notesUseCase: NotesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(AddEditNoteState())
    val state: State<AddEditNoteState> = _state

    val addOrEditNoteForm = AddOrEditNoteForm()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val uiEventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>("noteId")?.let {
            onEvents(AddEditNotesEvents.GetNoteByIdEvent(it))
        }
    }

    fun onEvents(addEditNotesEvents: AddEditNotesEvents) {
        when (addEditNotesEvents) {
            is AddEditNotesEvents.UpsertNotesEvent -> {
                addOrEditNote()
            }

            is AddEditNotesEvents.GetNoteByIdEvent -> {
                viewModelScope.launch {
                    if (addEditNotesEvents.noteId < 0) return@launch
                    notesUseCase.getNote(addEditNotesEvents.noteId).collectLatest {
                        _state.value = state.value.copy(
                            selectedNoteOrCreated = it,
                            addOrEditNote = false
                        )

                        addOrEditNoteForm.titleTextFiled.setTextValue(it.title)
                        addOrEditNoteForm.titleTextFiled.isHintVisible = false

                        addOrEditNoteForm.contentTextField.setTextValue(it.content)
                        addOrEditNoteForm.contentTextField.isHintVisible = false

                        addOrEditNoteForm.setColorsValue(it.color)
                    }
                }
            }

            is AddEditNotesEvents.EnteredTitle -> {
                addOrEditNoteForm.titleTextFiled.setTextValue(addEditNotesEvents.value)

            }

            is AddEditNotesEvents.EnteredContent -> {
                addOrEditNoteForm.contentTextField.setTextValue(addEditNotesEvents.value)
            }

            is AddEditNotesEvents.ChangeColor -> {
                addOrEditNoteForm.setColorsValue(addEditNotesEvents.color)
            }

            is AddEditNotesEvents.ChangeTitleFocus -> {
                addOrEditNoteForm.titleTextFiled.setIsFocused(addEditNotesEvents.focusState.isFocused)
            }

            is AddEditNotesEvents.ChangeContentFocus -> {
                addOrEditNoteForm.contentTextField.setIsFocused(addEditNotesEvents.focusState.isFocused)
            }
        }
    }

    private fun addOrEditNote() {
        viewModelScope.launch {
            if (addOrEditNoteForm.titleTextFiled.text.value != addOrEditNoteForm.titleTextFiled.hint) {
                _state.value.selectedNoteOrCreated.title =
                    addOrEditNoteForm.titleTextFiled.text.value
            }

            if (addOrEditNoteForm.contentTextField.text.value != addOrEditNoteForm.contentTextField.hint) {
                _state.value.selectedNoteOrCreated.content = addOrEditNoteForm.contentTextField.hint
            }
            _state.value.selectedNoteOrCreated.color = addOrEditNoteForm.colorRadioButtonField.value
            try {
                notesUseCase
                    .upsertNote(state.value.addOrEditNote, state.value.selectedNoteOrCreated)
                _eventFlow.emit(UiEvent.SaveNote)

            } catch (ex: CustomException) {
                ex.printStackTrace()
                _eventFlow.emit(
                    UiEvent.ShowSnackbar(ex.message ?: "couldn't save note")
                )
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }
}