package com.example.bookshelfapp.ui.screens.detail


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelfapp.BookshelfApplication
import com.example.bookshelfapp.data.remote.BooksListRepository
import com.example.bookshelfapp.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val booksListRepository: BooksListRepository
    ): ViewModel() {
    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailUiState: StateFlow<DetailUiState> = _detailUiState.asStateFlow()
    private val bookId: String = checkNotNull(savedStateHandle["bookId"])

    init {
        getBookInfo(bookId)
    }

    fun repeat(){
        getBookInfo()

    }
    fun getBookInfo(path: String = bookId ) = viewModelScope.launch {
        try {
            val bookInfo = booksListRepository.getBookInfo(path)
            giveSuccessStatus(bookInfo)
        } catch (e: IOException){
            giveErrorStatus()
        } catch (e: HttpException){
            giveErrorStatus()
        }
    }

    private fun giveSuccessStatus(book: Book){
        _detailUiState.value = DetailUiState.Success(
            title = book.volumeInfo.title,
            description = book.volumeInfo.description,
            author = book.volumeInfo.authors?.joinToString(),
            imageUrl = book.volumeInfo.imageLinks?.smallThumbnail ?: "",
            previewLink = book.volumeInfo.previewLink ?: ""
        )

    }
    private fun giveErrorStatus(){
        _detailUiState.value = DetailUiState.Error

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BookshelfApplication)
                val booksListRepository = application.container.booksListRepository
                DetailViewModel(this.createSavedStateHandle() ,booksListRepository = booksListRepository)
            }
        }
    }
}