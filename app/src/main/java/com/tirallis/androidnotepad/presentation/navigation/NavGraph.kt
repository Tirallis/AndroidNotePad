package com.tirallis.androidnotepad.presentation.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tirallis.androidnotepad.presentation.screens.creation.CreateNoteScreen
import com.tirallis.androidnotepad.presentation.screens.easterEggs.EasterEggScreen
import com.tirallis.androidnotepad.presentation.screens.editing.EditNoteScreen
import com.tirallis.androidnotepad.presentation.screens.notes.NotesScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Notes.route
    ) {
        composable(Screen.Notes.route) {
            NotesScreen(
                onNoteClick = {
                    navController.navigate(Screen.EditNote.createRoute(noteId = it.id))
                },
                onAddNoteClick = {
                    navController.navigate(Screen.CreateNote.route)
                },
                onTitleClick = {
                    navController.navigate(Screen.EasterEgg.route)
                }
            )
        }
        composable(Screen.CreateNote.route) {
            CreateNoteScreen(
                onFinished = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.EditNote.route) {
            val noteId = Screen.EditNote.getNoteId(it.arguments)
            EditNoteScreen(
                noteId = noteId,
                onFinished = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.EasterEgg.route) {
            EasterEggScreen(
                onFinished = {
                    navController.popBackStack()
                }
            )
        }
    }
}

sealed class Screen(
    val route: String
) {
    data object Notes : Screen("main")
    data object CreateNote : Screen("create")
    data object EasterEgg : Screen("eggs")
    data object EditNote: Screen("edit/{note_id}") {
        fun createRoute(noteId: Int): String {
            return "edit/$noteId"
        }

        fun getNoteId(arguments: Bundle?): Int {
            return arguments?.getString("note_id")?.toInt() ?: 0
        }
    }
}