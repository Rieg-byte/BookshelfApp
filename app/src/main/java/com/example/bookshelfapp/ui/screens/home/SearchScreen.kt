package com.example.bookshelfapp.ui.screens.home

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookshelfapp.model.BookListResponse
import com.example.bookshelfapp.model.VolumeInfo
import com.example.bookshelfapp.ui.components.BookImage
import com.example.bookshelfapp.ui.components.DefaultScreen
import com.example.bookshelfapp.ui.components.ErrorScreen
import com.example.bookshelfapp.ui.components.LoadingScreen
import com.example.bookshelfapp.ui.components.NotFoundScreen
import com.example.bookshelfapp.ui.screens.detail.DetailViewModel

@Composable
fun SearchScreen(
    searchUiState: SearchUiState,
    navigateToDetailScreen: () -> Unit,
    searchViewModel: SearchViewModel,
    detailViewModel: DetailViewModel,
    modifier: Modifier = Modifier
) {
    when (searchUiState) {
        is SearchUiState.Loading -> LoadingScreen()
        is SearchUiState.Success -> BooksListScreen(
            searchUiState.bookListResponse,
            navigateToDetailScreen,
            detailViewModel
        )

        is SearchUiState.Error -> ErrorScreen({ searchViewModel.repeatInput() })
        is SearchUiState.NotFound -> NotFoundScreen(searchViewModel.userInput)
        is SearchUiState.Default -> DefaultScreen()
    }

}

/**
 * Выводит список книг
 */
@Composable
fun BooksListScreen(
    bookListResponse: BookListResponse,
    navigateToDetailScreen: () -> Unit,
    detailViewModel: DetailViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        bookListResponse.items?.let {
            items(it) {
                BookItem(
                    volumeInfo = it.volumeInfo,
                    navigateToDetailScreen = {
                        navigateToDetailScreen()
                        detailViewModel.getBookInfo(it.id)
                    }
                )
            }
        }
    }
}


@Composable
fun BookItem(
    volumeInfo: VolumeInfo,
    navigateToDetailScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
            .clickable { navigateToDetailScreen() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            BookImage(volumeInfo.imageLinks)
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = volumeInfo.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                volumeInfo.authors?.let {
                    Text(
                        text = it.joinToString(),
                        fontSize = 14.sp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }

        }
    }
}






