package org.one.ummah.dev.notesapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.one.ummah.dev.notesapp.R
import org.one.ummah.dev.notesapp.enum.SortAscOrDesc
import org.one.ummah.dev.notesapp.enum.SortByColumn
import org.one.ummah.dev.notesapp.ui.components.DefaultRadioButton
import org.one.ummah.dev.notesapp.ui.components.NoteItem
import org.one.ummah.dev.notesapp.ui.events.HomeNotesEvents
import org.one.ummah.dev.notesapp.ui.stats.NoteState
import org.one.ummah.dev.notesapp.ui.stats.Screen
import org.one.ummah.dev.notesapp.ui.view_models.HomeNotesViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun setupHomeScreen(
    navController: NavController,
    viewModel: HomeNotesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        floatingActionButton = {
            addNoteButton {
                navController.navigate(Screen.AddOrEditNoteScreen.route)
            }
        }
    ) {
        Column {
            setupHeadersHomeScreen(
                sortByColumn = state.sortByColumn,
                sortAscOrDesc = state.sortAscOrDesc,
                isOrderSectionVisible = state.isOrderSectionVisible,
                onChangeOfOrders = { sortByColumn, sortByAscOrDesc ->
                    viewModel.onEvent(
                        HomeNotesEvents.OrderNotesEvent(
                            sortByColumn,
                            sortByAscOrDesc
                        )
                    )
                },
                onChangeOfOrderSectionVisibility = {
                    viewModel.onEvent(HomeNotesEvents.ToggleOrderSection)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            setupBodiesHomeScreen(
                state = state,
                navController = navController,
                viewModel = viewModel,
                scope = scope,
                snackbarHostState = snackBarHostState
            )
        }

    }

}

@Composable
fun setupBodiesHomeScreen(
    state: NoteState,
    navController: NavController,
    viewModel: HomeNotesViewModel,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(state.notesOrdered) { note ->
            NoteItem(
                note = note,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(
                            Screen.AddOrEditNoteScreen.route +
                                    "?noteId=${note.id}&noteColor=${note.color}"
                        )
                    },
                onDeleteClick = {
                    viewModel.onEvent(HomeNotesEvents.DeleteNoteEvent(note))
                    scope.launch {
                        val result = snackbarHostState.showSnackbar(
                            message = "Note deleted",
                            actionLabel = "Undo"
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            viewModel.onEvent(HomeNotesEvents.RestoreNote)
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun setupHeadersHomeScreen(
    modifier: Modifier = Modifier,
    sortAscOrDesc: SortAscOrDesc,
    sortByColumn: SortByColumn,
    isOrderSectionVisible: Boolean,
    onChangeOfOrderSectionVisibility: () -> Unit,
    onChangeOfOrders: (sortByColumn: SortByColumn, sortAscOrDesc: SortAscOrDesc) -> Unit
) {
    val headersHeight =
        LocalConfiguration.current.screenHeightDp * 0.07
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(headersHeight.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(8F),
                fontStyle = MaterialTheme.typography.headlineMedium.fontStyle,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                text = stringResource(id = R.string.home_notes_header)
            )
            IconButton(
                modifier = Modifier.weight(2f),
                onClick = { onChangeOfOrderSectionVisibility.invoke() }) {
                Icon(
                    Icons.Default.Sort,
                    contentDescription = "sort"
                )
            }
        }
        AnimatedVisibility(
            visible = isOrderSectionVisible,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {

            setupSort(
                modifier = Modifier,
                sortAscOrDesc = sortAscOrDesc,
                sortByColumn = sortByColumn,
                onSelect = onChangeOfOrders
            )
        }

    }
}

@Composable
fun setupSort(
    modifier: Modifier = Modifier,
    sortByColumn: SortByColumn = SortByColumn.DEFAULT_SORT,
    sortAscOrDesc: SortAscOrDesc = SortAscOrDesc.DEFAULT_SORT,
    onSelect: (sortByColumn: SortByColumn, sortAscOrDesc: SortAscOrDesc) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            SortByColumn.values().forEachIndexed { index, it ->
                DefaultRadioButton(
                    text = it.label,
                    selected = it == sortByColumn,
                    onSelect = { onSelect(it, sortAscOrDesc) })
                if (index != SortByColumn.values().lastIndex) Spacer(modifier = Modifier.width(8.dp))
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Row(Modifier.fillMaxWidth()) {
            SortAscOrDesc.values().forEachIndexed { index, it ->
                DefaultRadioButton(
                    text = it.Label,
                    selected = it == sortAscOrDesc,
                    onSelect = { onSelect(sortByColumn, it) })
                if (index != SortAscOrDesc.values().lastIndex) Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Composable
fun addNoteButton(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(
            Icons.Default.Add,
            contentDescription = "add"
        )
    }

}
