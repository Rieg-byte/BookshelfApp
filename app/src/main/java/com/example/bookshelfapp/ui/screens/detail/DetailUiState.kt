package com.example.bookshelfapp.ui.screens.detail

import com.example.bookshelfapp.model.Book

sealed interface DetailUiState {
    data class Success(
        val book: Book
    ): DetailUiState
    object Error: DetailUiState
    object Loading: DetailUiState
}