package org.one.ummah.dev.notesapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.one.ummah.dev.notesapp.ui.components.TransparentHintTextField
import org.one.ummah.dev.notesapp.ui.events.AddEditNotesEvents
import org.one.ummah.dev.notesapp.ui.view_models.AddEditViewModel
import org.one.ummah.dev.notesapp.utils.GlobalConstants
import org.one.ummah.dev.notesapp.utils.ObjectUtils

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteColor: String,
    noteId: Int,
    viewModel: AddEditViewModel = hiltViewModel()
) {
    val titleState = viewModel.addOrEditNoteForm.titleTextFiled
    val contentState = viewModel.addOrEditNoteForm.contentTextField

    val scaffoldState = remember {
        SnackbarHostState()
    }
    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(ObjectUtils.colorsBaseToFront(if (noteColor != "-1") noteColor else viewModel.addOrEditNoteForm.colorRadioButtonField.value))
        )
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.uiEventFlow.collectLatest { event ->
            when (event) {
                is AddEditViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.showSnackbar(
                        message = event.message
                    )
                }

                is AddEditViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvents(AddEditNotesEvents.UpsertNotesEvent)
                },
                modifier = Modifier.background(MaterialTheme.colorScheme.primary)
            ) {
                Icon(imageVector = Icons.Filled.Save, contentDescription = "Save note")
            }
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimatable.value)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                GlobalConstants.COLORS.forEach { color ->
                    val colorInt = ObjectUtils.colorsBaseToFront(color)
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(Color(colorInt), CircleShape)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.addOrEditNoteForm.colorRadioButtonField.value == color) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvents(AddEditNotesEvents.ChangeColor(color))
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(48.dp))
            TransparentHintTextField(
                text = titleState.text.value,
                hint = titleState.hint,
                onValueChange = {
                    viewModel.onEvents(AddEditNotesEvents.EnteredTitle(it))
                },
                onFocusChange = {
                    viewModel.onEvents(AddEditNotesEvents.ChangeTitleFocus(it))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(32.dp))
            TransparentHintTextField(
                text = contentState.text.value,
                hint = contentState.hint,
                onValueChange = {
                    viewModel.onEvents(AddEditNotesEvents.EnteredContent(it))
                },
                onFocusChange = {
                    viewModel.onEvents(AddEditNotesEvents.ChangeContentFocus(it))
                },
                isHintVisible = contentState.isHintVisible,
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}