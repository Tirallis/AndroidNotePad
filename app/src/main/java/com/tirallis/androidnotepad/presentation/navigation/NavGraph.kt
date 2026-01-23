package com.tirallis.androidnotepad.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.tirallis.androidnotepad.presentation.screens.creation.CreateNoteScreen
import com.tirallis.androidnotepad.presentation.screens.easterEggs.EasterEggScreen
import com.tirallis.androidnotepad.presentation.screens.editing.EditNoteScreen
import com.tirallis.androidnotepad.presentation.screens.notes.NotesScreen

@Composable
fun NavGraph() {
    val screen = remember {
        mutableStateOf<Screen>(Screen.Notes)
    }

    when (val currentScreen = screen.value) {
        Screen.CreateNote -> {
            CreateNoteScreen(
                onFinished = {
                    screen.value = Screen.Notes
                }
            )
        }

        Screen.EasterEgg -> {
            EasterEggScreen(
                onFinished = {
                    screen.value = Screen.Notes
                }
            )
        }

        is Screen.EditNote -> {
            EditNoteScreen(
                noteId = currentScreen.noteId,
                onFinished = {
                    screen.value = Screen.Notes
                }
            )
        }

        Screen.Notes -> {
            NotesScreen(
                onNoteClick = {
                    screen.value = Screen.EditNote(it.id)
                },
                onAddNoteClick = {
                    screen.value = Screen.CreateNote
                },
                onTitleClick = {
                    screen.value = Screen.EasterEgg
                }
            )
        }
    }
}

sealed interface Screen {

    data object Notes : Screen
    data object CreateNote : Screen
    data object EasterEgg : Screen
    data class EditNote(
        val noteId: Int
    ) : Screen


}