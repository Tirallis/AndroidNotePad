package com.tirallis.androidnotepad.domain

class SwitchPinnedStatusUseCase(
    private val repository: NotesRepository
) {

    operator fun invoke(noteId: Int) {
        return repository.switchPinnedStatus(noteId)
    }

}