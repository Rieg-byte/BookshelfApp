package com.example.bookshelfapp.ui.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelfapp.R
import com.example.bookshelfapp.ui.components.BookImage
import com.example.bookshelfapp.ui.components.ButtonPreview
import com.example.bookshelfapp.ui.components.ErrorScreen
import com.example.bookshelfapp.ui.components.LoadingScreen
import com.example.bookshelfapp.ui.icons.BookshelfIcons

@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel = viewModel(factory = DetailViewModel.Factory),
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val detailUiState by detailViewModel.detailUiState.collectAsState()
    Column(modifier = modifier.fillMaxSize()) {
        DetailAppBar(navigateUp = navigateUp)
        DetailBody(
            detailUiState = detailUiState,
            detailViewModel = detailViewModel
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailAppBar(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = BookshelfIcons.ArrowBack,
                    contentDescription = stringResource(id = R.string.back_button)
                )
            }
        }
    )
}

@Composable
private fun DetailBody(
    detailUiState: DetailUiState,
    detailViewModel: DetailViewModel,
    modifier: Modifier = Modifier
) {
    when (detailUiState) {
        is DetailUiState.Success ->
            DetailInfoBook(
                {detailViewModel.insertBook(detailUiState.bookInfo)},
                title = detailUiState.bookInfo.title,
                author = detailUiState.bookInfo.author,
                description = detailUiState.bookInfo.description,
                imageUrl = detailUiState.bookInfo.imageUrl,
                previewLink = detailUiState.bookInfo.previewLink
            )

        is DetailUiState.Loading -> LoadingScreen()
        is DetailUiState.Error -> ErrorScreen(onRepeat = detailViewModel::repeat)
    }
}


@Composable
private fun DetailInfoBook(
    insertBook: () -> Unit,
    title: String,
    author: String,
    description: String,
    imageUrl: String,
    previewLink: String,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier.height(180.dp)
        ) {
            BookImage(imageUrl = imageUrl)
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 5
                )
                Text(
                    text = author,
                    fontSize = 14.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.description),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = description,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.authors),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = author,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        IconButton(onClick = insertBook) {
            Icon(imageVector = Icons.Filled.FavoriteBorder, contentDescription = "")
        }
        ButtonPreview(
            context = context,
            previewLink = previewLink
        )
    }

}


