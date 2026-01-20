package com.tirallis.androidnotepad.domain

data class Note(
    val id: Int,
    val title: String,
    val text: String,
    val updatedAt: Long,
    val isPinned: Boolean
)
