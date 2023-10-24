package com.example.bookshelfapp.ui.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelfapp.ui.screens.search.BookItem

@Composable
fun FavoritesScreen(
    favoritesViewModel: FavoritesViewModel = viewModel(factory = FavoritesViewModel.Factory)
){
    val favoritesUiState by favoritesViewModel.favoritesUiState.collectAsState()
    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(favoritesUiState.listOfFavorites) {
                BookItem(title = it.title, author = it.author, imageUrl = it.imageUrl, onBookItemClick = { /*TODO*/ })
            }
        }
    }
}