package com.example.bookshelfapp.ui.screens.search

import com.example.bookshelfapp.data.remote.model.Item


sealed interface SearchUiState {
    data class Success(
        val listOfBooks: List<Item> = emptyList()
    ): SearchUiState
    object Loading: SearchUiState
    object Error: SearchUiState
    data class NotFound(
        val userInput: String = ""
    ): SearchUiState
    object Default: SearchUiState
}