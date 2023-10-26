package com.example.bookshelfapp.data.remote.model

data class Book(
    val id: String,
    val volumeInfo: VolumeInfo
) {
    fun convertToBookInfo() =
        BookInfo(
        id = id,
        title = volumeInfo.title,
        description = volumeInfo.description ?: "",
        author = volumeInfo.authors?.joinToString() ?: "",
        imageUrl = volumeInfo.imageLinks?.smallThumbnail ?: "",
        previewLink = volumeInfo.previewLink ?: ""
    )
    fun convertToItem() = Item(
        id = id,
        author = volumeInfo.authors?.joinToString() ?: "",
        title = volumeInfo.title,
        imageUrl = volumeInfo.imageLinks?.smallThumbnail ?: "",
    )
}

