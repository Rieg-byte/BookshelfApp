package com.example.bookshelfapp.ui.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelfapp.R
import com.example.bookshelfapp.data.local.entities.Favorite
import com.example.bookshelfapp.ui.components.BookCard

@Composable
fun FavoritesScreen(
    favoritesViewModel: FavoritesViewModel = viewModel(factory = FavoritesViewModel.Factory),
    navigateToDetailScreen: (String) -> Unit
){
    val favoritesUiState by favoritesViewModel.favoritesUiState.collectAsState()
    if (favoritesUiState.listOfFavorites.isEmpty()) {
        FavoritesEmptyScreen()
    } else {
        FavoritesListScreen(
            listOfFavorites = favoritesUiState.listOfFavorites,
            favoritesViewModel = favoritesViewModel,
            navigateToDetailScreen = navigateToDetailScreen
        )
    }
}

@Composable
fun FavoritesEmptyScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val text = stringResource(id = R.string.favorites_is_empty)
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(text.substringBefore("\n"))
                }
                append("\n")
                append(text.substringAfter("\n"))
            },
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun FavoritesListScreen(
    listOfFavorites: List<Favorite>,
    favoritesViewModel: FavoritesViewModel,
    navigateToDetailScreen: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(
            count = listOfFavorites.size,
            key = {
                listOfFavorites[it].id
            },
            itemContent = {index ->
                val favoriteData = listOfFavorites[index]
                BookCard(
                    title = favoriteData.title,
                    author = favoriteData.author,
                    imageUrl = favoriteData.imageUrl,
                    showButtonDelete = true,
                    navigateToDetailScreen = {navigateToDetailScreen(favoriteData.id)},
                    onDeleteBook = {favoritesViewModel.deleteBook(favoriteData)}
                )
            }

        )
    }
}