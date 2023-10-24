package com.example.bookshelfapp.ui.screens.detail

import com.example.bookshelfapp.data.remote.model.BookInfo


sealed interface DetailUiState{
   data class Success(
      val bookInfo: BookInfo,
      val showAlertDialog: Boolean = false
   ): DetailUiState
   object Loading: DetailUiState
   object Error: DetailUiState
}


