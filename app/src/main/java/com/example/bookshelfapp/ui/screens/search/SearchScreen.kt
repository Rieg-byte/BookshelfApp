package com.example.bookshelfapp.ui.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookshelfapp.R
import com.example.bookshelfapp.model.BookItem
import com.example.bookshelfapp.ui.components.BookImage
import com.example.bookshelfapp.ui.components.DefaultScreen
import com.example.bookshelfapp.ui.components.ErrorScreen
import com.example.bookshelfapp.ui.components.LoadingScreen
import com.example.bookshelfapp.ui.components.NotFoundScreen

@Composable
fun SearchScreen(
    navigateToDetailScreen: (String) -> Unit,
    searchViewModel: SearchViewModel,
) {
    val searchUiState by searchViewModel.searchUiState.collectAsState()
    SearchBody(
        searchUiState = searchUiState,
        onBookItemClick = navigateToDetailScreen,
        onRepeat = searchViewModel::repeat
    )


}
@Composable
fun SearchBody(
    searchUiState: SearchUiState,
    onBookItemClick: (String) -> Unit,
    onRepeat: () -> Unit,
    modifier: Modifier = Modifier

){
    when (searchUiState) {
        is SearchUiState.Loading -> LoadingScreen()
        is SearchUiState.Success -> BooksListScreen(
            searchUiState.listOfBooks,
            onBookItemClick
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
    listOfBooks: List<BookItem>,
    onBookItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(listOfBooks) {
            BookItem(
                title = it.title,
                author = it.author ?: stringResource(id = R.string.author_not_specified),
                imageUrl = it.imageUrl,
                onBookItemClick = {onBookItemClick(it.id)}
            )
        }
    }
}


@Composable
fun BookItem(
    title: String,
    author: String,
    imageUrl: String,
    onBookItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
            .clickable { onBookItemClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            BookImage(imageUrl)
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                Text(
                    text = author,
                    fontSize = 14.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
}






