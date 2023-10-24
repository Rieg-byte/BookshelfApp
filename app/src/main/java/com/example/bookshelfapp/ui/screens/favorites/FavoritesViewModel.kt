package com.example.bookshelfapp.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelfapp.BookshelfApplication
import com.example.bookshelfapp.data.FavoritesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val favoritesRepository: FavoritesRepository
): ViewModel() {
    private val _favoriteUiState = MutableStateFlow(FavoritesUiState())
    val favoritesUiState = _favoriteUiState.asStateFlow()

    init {
        getAllFavorites()
    }

    private fun getAllFavorites() = viewModelScope.launch {
        favoritesRepository.getAll().collect { listOfFavorites ->
            _favoriteUiState.value = FavoritesUiState(listOfFavorites)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BookshelfApplication)
                val favoritesRepository = application.container.favoritesRepository
                FavoritesViewModel(favoritesRepository = favoritesRepository)
            }
        }
    }
}