package com.example.bookshelfapp.model


data class BookListResponse(
    val totalItems: String,
    val items: List<Book>?
)


