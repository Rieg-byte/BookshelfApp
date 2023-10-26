package com.example.bookshelfapp.data.remote.model


data class BookList(
    val totalItems: String,
    val items: List<Book>?
) {
    fun convertToItemList(): List<Item> = items?.map { it.convertToItem() } ?: emptyList()
}


