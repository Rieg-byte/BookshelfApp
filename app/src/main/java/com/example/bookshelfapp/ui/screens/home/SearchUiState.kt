package com.example.bookshelfapp.ui.screens.home

import com.example.bookshelfapp.model.SearchResponse

sealed interface SearchUiState {
    data class Success(
        val searchResponse: SearchResponse
    ): SearchUiState
    object Loading: SearchUiState
    object Error: SearchUiState
    object NotFound: SearchUiState
    object Default: SearchUiState
}
