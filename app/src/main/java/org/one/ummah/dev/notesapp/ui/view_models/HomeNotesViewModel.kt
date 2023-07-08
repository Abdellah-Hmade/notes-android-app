package org.one.ummah.dev.notesapp.ui.view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.one.ummah.dev.notesapp.enum.SortAscOrDesc
import org.one.ummah.dev.notesapp.enum.SortByColumn
import org.one.ummah.dev.notesapp.ui.events.HomeNotesEvents
import org.one.ummah.dev.notesapp.ui.stats.NoteState
import org.one.ummah.dev.notesapp.use_case.NotesUseCase
import javax.inject.Inject

@HiltViewModel
class HomeNotesViewModel @Inject constructor(
    private val notesUseCase: NotesUseCase
) : ViewModel() {
    private val _state = mutableStateOf(NoteState())
    val state: State<NoteState> = _state
    var getNotesJob: Job? = null

    init {
        getNotes(SortByColumn.DEFAULT_SORT, SortAscOrDesc.DEFAULT_SORT)
    }

    fun onEvent(homeNotesEvents: HomeNotesEvents) {
        when (
            homeNotesEvents
        ) {
            is HomeNotesEvents.OrderNotesEvent -> {
                if (state.value.sortByColumn == homeNotesEvents.sortByColumn && state.value.sortAscOrDesc == homeNotesEvents.sortAscOrDesc) {
                    return
                }
                getNotes(homeNotesEvents.sortByColumn, homeNotesEvents.sortAscOrDesc)
            }

            is HomeNotesEvents.DeleteNoteEvent -> {
                viewModelScope.launch {
                    notesUseCase.deleteNote.invoke(homeNotesEvents.notesDto)
                    _state.value = state.value.copy(
                        recentlyDeletedNote = homeNotesEvents.notesDto
                    )
                }
            }

            is HomeNotesEvents.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }

            is HomeNotesEvents.RestoreNote -> {
                viewModelScope.launch {
                    notesUseCase.upsertNote.invoke(true, state.value.recentlyDeletedNote)
                    _state.value = state.value.copy(
                        recentlyDeletedNote = null
                    )
                }
            }

            else -> {}
        }
    }

    private fun getNotes(sortByColumn: SortByColumn, sortAscOrDesc: SortAscOrDesc) {
        getNotesJob?.cancel()
        getNotesJob = notesUseCase.getNotesOrdered(sortAscOrDesc, sortByColumn)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notesOrdered = notes,
                    sortByColumn = sortByColumn,
                    sortAscOrDesc = sortAscOrDesc
                )
            }
            .launchIn(viewModelScope)
    }
}