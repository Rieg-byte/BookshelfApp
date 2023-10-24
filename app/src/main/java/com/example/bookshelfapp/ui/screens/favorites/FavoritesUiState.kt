package com.example.bookshelfapp.ui.screens.favorites

import com.example.bookshelfapp.data.local.entities.Favorite

data class FavoritesUiState(
    val listOfFavorites: List<Favorite> = emptyList()
)
