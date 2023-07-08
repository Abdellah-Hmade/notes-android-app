package org.one.ummah.dev.notesapp.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.one.ummah.dev.notesapp.ui.screens.AddEditNoteScreen
import org.one.ummah.dev.notesapp.ui.screens.setupHomeScreen
import org.one.ummah.dev.notesapp.ui.stats.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun setupNavigationsScreen(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(route = Screen.HomeScreen.route) {
            setupHomeScreen(navController = navController)
        }
        composable(
            route = Screen.AddOrEditNoteScreen.route +
                    "?noteId={noteId}&noteColor={noteColor}",
            arguments = listOf(
                navArgument(
                    name = "noteId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(
                    name = "noteColor"
                ) {
                    type = NavType.StringType
                    defaultValue = "-1"
                },
            )
        ) {
            val color = it.arguments?.getString("noteColor") ?: "-1"
            val noteId = it.arguments?.getInt("noteId") ?: -1
            AddEditNoteScreen(
                navController = navController,
                noteColor = color,
                noteId = noteId
            )
        }
    }
}