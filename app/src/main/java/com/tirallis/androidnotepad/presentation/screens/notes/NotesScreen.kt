@file:OptIn(ExperimentalFoundationApi::class)

package com.tirallis.androidnotepad.presentation.screens.notes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tirallis.androidnotepad.domain.Note
import com.tirallis.androidnotepad.presentation.ui.theme.OtherNotesColors
import com.tirallis.androidnotepad.presentation.ui.theme.PinnedNotesColors
import com.tirallis.androidnotepad.presentation.utils.DateFormater


@Composable
fun NotesScreen(
    modifier: Modifier = Modifier,
    viewModel: NotesViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    LazyColumn(
        modifier = modifier
            .padding(top = 48.dp),
    ) {
        item {
            Title(
                modifier.padding(horizontal = 24.dp),
                text = "Все Заметки"
            )
        }
        item {
            Spacer(modifier = modifier.height(16.dp))
        }
        item {
            SearchBar(
                modifier.padding(horizontal = 24.dp),
                query = state.query,
                onQueryChange = { viewModel.processCommand(NotesCommand.InputSearchQuery(it)) }
            )
        }
        item {
            Spacer(modifier = modifier.height(24.dp))
        }
        item {
            Subtitle(
                modifier.padding(horizontal = 24.dp),
                text = "Закреплено"
            )
        }
        item {
            Spacer(modifier = modifier.height(16.dp))
        }
        item {
            LazyRow(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 24.dp)
            ) {
                itemsIndexed(
                    items = state.pinnedNotes,
                    key = { _, note -> note.id }
                ) { index, note ->
                    NoteCard(
                        modifier = modifier.widthIn(max = 160.dp),
                        note = note,
                        onNoteClick = { note ->
                            viewModel.processCommand(NotesCommand.EditNote(note))
                        },
                        onNoteHold = { note ->
                            viewModel.processCommand(NotesCommand.SwitchPinnedStatus(note.id))
                        },
                        onNoteDoubleClick = { note ->
                            viewModel.processCommand(NotesCommand.DeleteNote(note.id))
                        },
                        backgroundColor = PinnedNotesColors[index % PinnedNotesColors.size]
                    )
                }
            }
        }

        item {
            Spacer(modifier = modifier.height(24.dp))
        }
        item {
            Subtitle(
                modifier = modifier.padding(horizontal = 24.dp),
                text = "Остальное"
            )
        }
        item {
            Spacer(modifier = modifier.height(16.dp))
        }
        itemsIndexed(
            items = state.otherNotes,
            key = { _, note -> note.id }
        ) { index, note ->
            NoteCard(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                note = note,
                onNoteClick = { note ->
                    viewModel.processCommand(NotesCommand.EditNote(note))
                },
                onNoteHold = { note ->
                    viewModel.processCommand(NotesCommand.SwitchPinnedStatus(note.id))
                },
                onNoteDoubleClick = { note ->
                    viewModel.processCommand(NotesCommand.DeleteNote(note.id))
                },
                backgroundColor = OtherNotesColors[index % OtherNotesColors.size],
            )
            Spacer(modifier = modifier.height(8.dp))
        }
    }
}

@Composable
private fun Title(
    modifier: Modifier = Modifier,
    text: String,
//    onTitleClick: () -> Unit
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                shape = RoundedCornerShape(10.dp)
            ),
        value = query,
        onValueChange = onQueryChange,
        placeholder = {
            Text(
                text = "Поиск..",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Поиск заметок",
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
private fun Subtitle(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    note: Note,
    backgroundColor: Color,
    onNoteClick: (Note) -> Unit,
    onNoteHold: (Note) -> Unit,
    onNoteDoubleClick: (Note) -> Unit,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .combinedClickable(
                onClick = {
                    onNoteClick(note)
                },
                onLongClick = {
                    onNoteHold(note)
                },
                onDoubleClick = {
                    onNoteDoubleClick(note)
                }
            )
            .padding(16.dp)
    ) {
        Text(
            text = note.title,
            fontSize = 14.sp,
            maxLines = 1,
            color = MaterialTheme.colorScheme.onSurface,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = modifier.height(8.dp))
        Text(
            text = DateFormater.formatDateToString(note.updatedAt),
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = modifier.height(24.dp))
        Text(
            text = note.text,
            fontSize = 16.sp,
            maxLines = 3,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            overflow = TextOverflow.Ellipsis,
        )
    }
}