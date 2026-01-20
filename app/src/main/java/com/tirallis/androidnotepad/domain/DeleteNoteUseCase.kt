package com.tirallis.androidnotepad.domain

class DeleteNoteUseCase(
    private val repository: NotesRepository
) {

    operator fun invoke(noteId: Int) {
        repository.deleteNode(noteId)
    }

}