package com.tirallis.androidnotepad.presentation.screens.notes

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun NotesScreen(
    modifier: Modifier = Modifier,
    viewModel: NotesViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .padding(top = 48.dp)
            .verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .horizontalScroll(state = rememberScrollState())
        ) {
            state.pinnedNotes.forEach { note ->
                Text(
                    modifier = Modifier.clickable {
                        viewModel.processCommand(NotesCommand.SwitchPinnedStatus(note.id))
                    },
                    text = "${note.title} - ${note.text} ",
                    fontSize = 24.sp
                )
            }
        }
        state.otherNotes.forEach { note ->
            Text(
                modifier = Modifier.clickable {
                    viewModel.processCommand(NotesCommand.SwitchPinnedStatus(note.id))
                },
                text = "${note.title} - ${note.text}",
                fontSize = 24.sp
            )
        }
    }
}