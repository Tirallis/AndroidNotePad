package com.tirallis.androidnotepad.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.tirallis.androidnotepad.presentation.navigation.NavGraph
import com.tirallis.androidnotepad.presentation.ui.theme.AndroidNotePadTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidNotePadTheme {
                NavGraph()
            }
        }
    }
}