package com.example.bookshelfapp.ui.screens.detail


import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelfapp.BookshelfApplication
import com.example.bookshelfapp.data.BooksRepository
import com.example.bookshelfapp.data.FavoritesRepository
import com.example.bookshelfapp.data.remote.model.BookInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val booksRepository: BooksRepository,
    private val favoritesRepository: FavoritesRepository
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

    fun isFavorite() = viewModelScope.launch {
        favoritesRepository.isFavoriteBook(bookId).collect{
            _detailUiState.value = (_detailUiState.value as DetailUiState.Success).copy(isFavorite = it)
        }
    }
    fun insertBook(bookInfo: BookInfo) {
        viewModelScope.launch {
            try {
                favoritesRepository.insertBook(bookInfo.convertBookInfoToFavoriteEntity())
            } catch(e: SQLiteConstraintException) {
                favoritesRepository.deleteBook(bookInfo.convertBookInfoToFavoriteEntity())
            }
        }
    }
    fun getBookInfo(path: String = bookId ) = viewModelScope.launch {
        try {
            val bookInfo = booksRepository.getBookInfo(path)
            giveSuccessStatus(bookInfo)
        } catch (e: IOException){
            giveErrorStatus()
        } catch (e: HttpException){
            giveErrorStatus()
        }
    }

    private fun giveSuccessStatus(bookInfo: BookInfo){
        _detailUiState.value = DetailUiState.Success(bookInfo)
        isFavorite()

    }
    private fun giveErrorStatus(){
        _detailUiState.value = DetailUiState.Error

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BookshelfApplication)
                val booksListRepository = application.container.booksRepository
                val favoritesRepository = application.container.favoritesRepository
                DetailViewModel(this.createSavedStateHandle() ,booksRepository = booksListRepository, favoritesRepository = favoritesRepository)
            }
        }
    }
}