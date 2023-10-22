package com.example.bookshelfapp.data.remote.model

data class VolumeInfo(
    val title: String,
    val authors: List<String>?,
    val description: String?,
    val imageLinks: ImageLinks?,
    val previewLink: String?
)