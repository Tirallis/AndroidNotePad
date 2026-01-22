package com.tirallis.androidnotepad.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.tirallis.androidnotepad.presentation.screens.notes.NotesScreen
import com.tirallis.androidnotepad.presentation.ui.theme.AndroidNotePadTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidNotePadTheme {
                NotesScreen(
                    onNoteClick = {
                        Log.d("MainActivity", "onNoteClick: $it")
                    },
                    onAddNoteClick = {
                        Log.d("MainActivity", "ActionButton was clicked.")
                    }
                )
            }
        }
    }
}