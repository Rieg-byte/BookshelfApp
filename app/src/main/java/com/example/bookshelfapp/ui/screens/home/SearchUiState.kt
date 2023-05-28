package com.example.bookshelfapp.ui.screens.home

import com.example.bookshelfapp.model.BookListResponse

sealed interface SearchUiState {
    data class Success(
        val bookListResponse: BookListResponse
    ): SearchUiState
    object Loading: SearchUiState
    object Error: SearchUiState
    object NotFound: SearchUiState
    object Default: SearchUiState
}
