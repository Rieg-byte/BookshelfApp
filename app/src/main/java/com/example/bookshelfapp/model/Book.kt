package com.example.bookshelfapp.model

data class Book(
    val id: String,
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    val title: String,
    val authors: List<String>?,
    val description: String?,
    val imageLinks: ImageLink?,
    val previewLink: String?
)

data class ImageLink(
    val smallThumbnail: String,
    val thumbnail: String
)