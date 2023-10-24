package com.example.bookshelfapp.data.remote.model

import com.example.bookshelfapp.data.local.entities.Favorite

data class BookInfo(
    val id: String,
    val title: String,
    val description: String,
    val author: String,
    val imageUrl: String,
    val previewLink: String
) {
    fun convertBookInfoToFavoriteEntity(): Favorite = Favorite(
        id = id,
        author = author,
        title = title,
        imageUrl = imageUrl
    )
}
