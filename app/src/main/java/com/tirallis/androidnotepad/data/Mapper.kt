package com.tirallis.androidnotepad.data

import com.tirallis.androidnotepad.domain.ContentItem
import com.tirallis.androidnotepad.domain.Note
import kotlinx.serialization.json.Json

fun Note.toDBModel(): NoteDBModel {
    val contentAsString = Json.encodeToString(content.toContentItemDBModels())
    return NoteDBModel(
        id,
        title,
        contentAsString,
        updatedAt,
        isPinned
    )
}

fun List<ContentItem>.toContentItemDBModels(): List<ContentItemDBModel> {
    return map { contentItem ->
        when (contentItem) {
            is ContentItem.Image -> {
                ContentItemDBModel.Image(url = contentItem.url)
            }
            is ContentItem.Text -> {
                ContentItemDBModel.Text(content = contentItem.content)
            }
        }
    }
}

fun NoteDBModel.toEntity(): Note {
    val contentItemDBModels = Json.decodeFromString<List<ContentItemDBModel>>(content)
    return Note(
        id,
        title,
        contentItemDBModels.toContentItems(),
        updatedAt,
        isPinned
    )
}

fun List<ContentItemDBModel>.toContentItems(): List<ContentItem> {
    return map { contentItemDBModel ->
        when (contentItemDBModel) {
            is ContentItemDBModel.Image -> {
                ContentItem.Image(url = contentItemDBModel.url)
            }
            is ContentItemDBModel.Text -> {
                ContentItem.Text(content = contentItemDBModel.content)
            }
        }
    }
}

fun List<NoteDBModel>.toEntities(): List<Note> {
    return map { it.toEntity() }
}