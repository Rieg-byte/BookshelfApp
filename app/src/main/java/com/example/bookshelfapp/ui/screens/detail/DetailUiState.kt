package com.example.bookshelfapp.ui.screens.detail


sealed interface DetailUiState{
   data class Success(
      val title: String = "",
      val description: String? = null,
      val author: String? = null,
      val imageUrl: String = "",
      val previewLink: String = ""
   ): DetailUiState
   object Loading: DetailUiState
   object Error: DetailUiState
}


