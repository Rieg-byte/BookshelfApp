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
import com.example.bookshelfapp.data.FavoritesRepository
import com.example.bookshelfapp.data.local.entities.Favorite
import com.example.bookshelfapp.data.remote.model.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class SearchViewModel(
    private val booksRepository: BooksRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {
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

    fun insertBook(item: Item) {
        viewModelScope.launch {
            favoritesRepository.insertBook(
                Favorite(
                    title = item.title,
                    author = "",
                    imageUrl = item.imageUrl,
                    selfLink = ""
                )
            )
        }
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
                val bookList = booksRepository.getBooksList(q)
                giveSuccessStatus(bookList)
            } catch (e: IOException) {
                giveErrorStatus()
            } catch (e: HttpException) {
                giveErrorStatus()
            }
        }
    }

    private fun giveSuccessStatus(bookList: List<Item>) {
        if (bookList != null) {
            _searchUiState.value = SearchUiState.Success(bookList)
        } else {
            _searchUiState.value = SearchUiState.NotFound(userInput)
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
                val favoritesRepository = application.container.favoritesRepository
                SearchViewModel(
                    booksRepository = booksListRepository,
                    favoritesRepository = favoritesRepository
                    )
            }
        }
    }
}


