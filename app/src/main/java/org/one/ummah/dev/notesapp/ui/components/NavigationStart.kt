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
fun SetupNavigationsScreen(navController: NavHostController) {

    NavHost(
        navController = navController, startDestination = Screen.HomeScreen.route
    ) {
        composable(
            route = Screen.HomeScreen.route + "?message={message}", arguments = listOf(
                navArgument(name = "message") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            val message = it.arguments?.getString("message")

            setupHomeScreen(navController = navController, message = message)
        }
        composable(
            route = Screen.AddOrEditNoteScreen.route + "?noteId={noteId}&noteColor={noteColor}",
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
                    type = NavType.LongType
                    defaultValue = -1
                },
            )
        ) {
            val color = it.arguments?.getLong("noteColor") ?: -1
            val noteId = it.arguments?.getInt("noteId") ?: -1
            AddEditNoteScreen(
                navController = navController, noteColor = color, noteId = noteId
            )
        }
    }
}