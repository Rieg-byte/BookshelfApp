package com.example.bookshelfapp.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelfapp.BookshelfApplication
import com.example.bookshelfapp.data.BooksListRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DetailViewModel(private val booksListRepository: BooksListRepository): ViewModel() {
    var detailUiState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    private var _path: String by mutableStateOf("")

    fun repeat(){
        getBookInfo()

    }
    fun getBookInfo(path: String = _path) = viewModelScope.launch {
        detailUiState = DetailUiState.Loading
        _path = path
        detailUiState = try {
            DetailUiState.Success(booksListRepository.getBookInfo(path))
        } catch (e: IOException){
            DetailUiState.Error
        } catch (e: HttpException){
            DetailUiState.Error
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BookshelfApplication)
                val booksListRepository = application.container.booksListRepository
                DetailViewModel(booksListRepository = booksListRepository)
            }
        }
    }
}