package com.example.bookshelfapp.ui.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelfapp.BookshelfApplication
import com.example.bookshelfapp.data.BooksRepository
import com.example.bookshelfapp.model.Book
import com.example.bookshelfapp.model.BookItem
import com.example.bookshelfapp.model.BookListResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class SearchViewModel(private val booksRepository: BooksRepository) : ViewModel() {
    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Default)
    val searchUiState: StateFlow<SearchUiState> = _searchUiState.asStateFlow()

    var userInput: String by mutableStateOf("")
        private set

    /**
     * Обновление пользовательского ввода
     */
    fun updateUserInput(newUserInput: String) {
        userInput = newUserInput
        _searchUiState.value = SearchUiState.Default

    }

    /**
     * Очистить поле ввода
     */
    fun clearUserInput() {
        userInput = ""
        _searchUiState.value = SearchUiState.Default
    }

    /**
     * Повторить ввод данных
     */
    fun repeat() {
        getBooksList(userInput)
    }

    /**
     * Получить список книг
     */
    fun getBooksList(q: String) {
        if (q.isBlank()) getDefaultStatus()
        else viewModelScope.launch {
            try {
                _searchUiState.value = SearchUiState.Loading
                val bookListResponse = booksRepository.getBooksListResponse(q)
                giveSuccessStatus(bookListResponse)
            } catch (e: IOException) {
                giveErrorStatus()
            } catch (e: HttpException) {
                giveErrorStatus()
            }
        }
    }

    private fun giveSuccessStatus(bookListResponse: BookListResponse) {
        val listOfBooks = bookListResponse.items
        if (listOfBooks != null) {
            _searchUiState.value = SearchUiState.Success(getBookItems(listOfBooks))
        } else {
            _searchUiState.value = SearchUiState.NotFound(userInput)
        }
    }

    private fun getBookItems(list: List<Book>): List<BookItem> {
        return list.map {
            BookItem(
                id = it.id,
                title = it.volumeInfo.title,
                imageUrl = it.volumeInfo.imageLinks?.smallThumbnail ?: "",
                author = it.volumeInfo.authors?.joinToString()
            )
        }
    }


    private fun giveErrorStatus() {
        _searchUiState.value = SearchUiState.Error

    }

    private fun getDefaultStatus() {
        _searchUiState.value = SearchUiState.Default
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookshelfApplication)
                val booksListRepository = application.container.booksRepository
                SearchViewModel(booksRepository = booksListRepository)
            }
        }
    }
}


