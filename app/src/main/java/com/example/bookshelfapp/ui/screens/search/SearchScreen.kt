package com.example.bookshelfapp.ui.screens.search

import androidx.compose.foundation.clickable
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
import com.example.bookshelfapp.data.remote.model.Item
import com.example.bookshelfapp.ui.components.BookCard
import com.example.bookshelfapp.ui.components.DefaultScreen
import com.example.bookshelfapp.ui.components.ErrorScreen
import com.example.bookshelfapp.ui.components.LoadingScreen
import com.example.bookshelfapp.ui.components.NotFoundScreen
import com.example.bookshelfapp.ui.components.SearchField


@Composable
fun SearchScreen(
    navigateToDetailScreen: (String) -> Unit,
    searchViewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory),
    modifier: Modifier = Modifier
) {
    val searchUiState by searchViewModel.searchUiState.collectAsState()
    Column(modifier = modifier.fillMaxSize()) {
        SearchTopBar(
            userInput = searchViewModel.userInput,
            updateUserInput = searchViewModel::updateUserInput,
            clearUserInput = searchViewModel::clearUserInput,
            onDone = {searchViewModel.getBooksList(searchViewModel.userInput)}
        )
        SearchBody(
            searchUiState = searchUiState,
            navigateToDetailScreen = navigateToDetailScreen,
            onRepeat = searchViewModel::repeat
        )
    }
}


@Composable
private fun SearchTopBar(
    userInput: String,
    updateUserInput: (String) -> Unit,
    clearUserInput: () -> Unit,
    onDone: () -> Unit
){
    SearchField(
        userInput = userInput,
        updateUserInput = updateUserInput,
        clearUserInput = clearUserInput,
        onDone = onDone
    )
}
@Composable
private fun SearchBody(
    searchUiState: SearchUiState,
    navigateToDetailScreen: (String) -> Unit,
    onRepeat: () -> Unit,
    modifier: Modifier = Modifier

){
    when (searchUiState) {
        is SearchUiState.Loading -> LoadingScreen()
        is SearchUiState.Success -> BooksListScreen(
            searchUiState.listOfBooks,
            navigateToDetailScreen
        )
        is SearchUiState.Error -> ErrorScreen(onRepeat)
        is SearchUiState.NotFound -> NotFoundScreen(searchUiState.userInput)
        is SearchUiState.Default -> DefaultScreen()
    }

}

/**
 * Выводит список книг
 */
@Composable
fun BooksListScreen(
    listOfBooks: List<Item>,
    onBookItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(listOfBooks) {
            BookCard(
                title = it.title,
                author = it.author,
                imageUrl = it.imageUrl,
                navigateToDetailScreen = {onBookItemClick(it.id)},
            )
        }
    }
}






