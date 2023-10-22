package com.example.bookshelfapp.ui.screens.detail


sealed interface DetailUiState{
   data class Success(
      val title: String = "",
      val description: String = "",
      val author: String = "",
      val imageUrl: String = "",
      val previewLink: String = ""
   ): DetailUiState
   object Loading: DetailUiState
   object Error: DetailUiState
}


