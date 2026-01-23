package com.tirallis.androidnotepad.presentation.screens.easterEggs

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EasterEggsViewModel : ViewModel() {

    private val _state = MutableStateFlow<EasterEggsState>(EasterEggsState.Initial)
    val state = _state.asStateFlow()

    fun processCommand(command: EasterEggsCommand) {
        when (command) {
            EasterEggsCommand.Back -> _state.update { EasterEggsState.Finished }
        }
    }
}

sealed interface EasterEggsCommand {
    data object Back : EasterEggsCommand
}

sealed interface EasterEggsState {
    data object Initial : EasterEggsState
    data object Finished : EasterEggsState

}