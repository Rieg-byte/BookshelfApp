package com.example.bookshelfapp.model


data class SearchResponse(
    val totalItems: String,
    val items: List<Book>?
)


