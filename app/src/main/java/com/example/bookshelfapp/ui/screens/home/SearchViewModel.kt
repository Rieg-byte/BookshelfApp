package com.example.bookshelfapp.ui.screens.home

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
import com.example.bookshelfapp.data.BooksListRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class SearchViewModel(private val booksListRepository: BooksListRepository): ViewModel() {
    var searchUiState: SearchUiState by mutableStateOf(SearchUiState.Default)
        private set

    /**
     * Поле ввода
     */
    var userInput: String by mutableStateOf("")
        private set


    /**
     * Обновление пользовательского ввода
     */
    fun updateUserInput(newUserInput: String){
        userInput = newUserInput
        searchUiState = SearchUiState.Default

    }

    /**
     * Очистить поле ввода
     */
    fun clearUserInput(){
        userInput = ""
        searchUiState = SearchUiState.Default
    }

    /**
     * Повторить ввод данных
     */
    fun repeatInput(){
        getBooksList(userInput)
    }

    fun getBookInfo(path: String){

    }
    /**
     * Получает список книг
     */
    fun getBooksList(q: String) = viewModelScope.launch {
        if (q.isBlank()) searchUiState = SearchUiState.Default
        else {
            searchUiState = SearchUiState.Loading
            searchUiState = try {
                val result = booksListRepository.getBooksListResponse(q)
                if (result.items == null) SearchUiState.NotFound
                else SearchUiState.Success(result)
            } catch (e: IOException) {
                SearchUiState.Error
            } catch (e: HttpException) {
                SearchUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookshelfApplication)
                val booksListRepository = application.container.booksListRepository
                SearchViewModel(booksListRepository = booksListRepository)
            }
        }
    }
}