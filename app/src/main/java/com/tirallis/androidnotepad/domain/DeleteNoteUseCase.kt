package com.tirallis.androidnotepad.domain

import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: NotesRepository
) {

    suspend operator fun invoke(noteId: Int) {
        repository.deleteNode(noteId)
    }

}