package com.example.bookshelfapp.ui.screens.search

import com.example.bookshelfapp.model.BookItem


sealed interface SearchUiState {
    data class Success(
        val listOfBooks: List<BookItem> = emptyList()
    ): SearchUiState
    object Loading: SearchUiState
    object Error: SearchUiState
    data class NotFound(
        val userInput: String = ""
    ): SearchUiState
    object Default: SearchUiState
}