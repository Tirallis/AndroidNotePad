@file:OptIn(ExperimentalCoroutinesApi::class)

package com.tirallis.androidnotepad.presentation.screens.notes

import androidx.lifecycle.ViewModel
import com.tirallis.androidnotepad.data.TestNotesRepositoryImpl
import com.tirallis.androidnotepad.domain.AddNoteUseCase
import com.tirallis.androidnotepad.domain.DeleteNoteUseCase
import com.tirallis.androidnotepad.domain.EditNoteUseCase
import com.tirallis.androidnotepad.domain.GetAllNotesUseCase
import com.tirallis.androidnotepad.domain.GetNoteUseCase
import com.tirallis.androidnotepad.domain.Note
import com.tirallis.androidnotepad.domain.SearchNotesUseCase
import com.tirallis.androidnotepad.domain.SwitchPinnedStatusUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class NotesViewModel : ViewModel() {
    private val repository = TestNotesRepositoryImpl
    private val addNoteUseCase = AddNoteUseCase(repository)
    private val editNoteUseCase = EditNoteUseCase(repository)
    private val deleteNoteUseCase = DeleteNoteUseCase(repository)
    private val getAllNotesUseCase = GetAllNotesUseCase(repository)
    private val getNoteUseCase = GetNoteUseCase(repository)
    private val searchNotesUseCase = SearchNotesUseCase(repository)
    private val switchPinnedStatusUseCase = SwitchPinnedStatusUseCase(repository)
    private val query = MutableStateFlow("")
    private val _state = MutableStateFlow(NotesScreenState())
    val state = _state.asStateFlow()
    val scope = CoroutineScope(Dispatchers.IO)

    //TODO: Не забудь удалить метод, нужен только для проверки
    private fun addSomeNotes() {
        repeat(1000) {
            addNoteUseCase(title = "Title №$it", content = "Content №$it")
        }

    }

    init {
        addSomeNotes()
        query
            .onEach { userInput ->
                _state.update { it.copy(query = userInput) }
            }
            .flatMapLatest { userInput ->
                if (userInput.isBlank()) {
                    getAllNotesUseCase()
                } else {
                    searchNotesUseCase(userInput.trim())
                }
            }
            .onEach {
                val pinnedNotes = it.filter { note -> note.isPinned }
                val otherNotes = it.filter { note -> !note.isPinned }
                _state.update { state ->
                    state.copy(
                        pinnedNotes = pinnedNotes,
                        otherNotes = otherNotes
                    )
                }
            }
            .launchIn(scope)
    }

    fun processCommand(command: NotesCommand) {
        when (command) {
            is NotesCommand.DeleteNote -> deleteNoteUseCase(command.noteId)
            is NotesCommand.EditNote -> {
                val note = getNoteUseCase(command.note.id)
                val title = command.note.title
                editNoteUseCase(note.copy(title = "$title edited"))
            }

            is NotesCommand.InputSearchQuery -> {
                query.update { command.query }
            }

            is NotesCommand.SwitchPinnedStatus -> {
                switchPinnedStatusUseCase(command.noteId)
            }
        }
    }
}

sealed interface NotesCommand {

    data class InputSearchQuery(val query: String) : NotesCommand
    data class SwitchPinnedStatus(val noteId: Int) : NotesCommand

    //Temp
    data class DeleteNote(val noteId: Int) : NotesCommand
    data class EditNote(val note: Note) : NotesCommand
}

data class NotesScreenState(
    val query: String = "",
    val pinnedNotes: List<Note> = listOf(),
    val otherNotes: List<Note> = listOf()
)