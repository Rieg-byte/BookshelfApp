package com.example.bookshelfapp.ui.screens.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookshelfapp.R
import com.example.bookshelfapp.model.VolumeInfo
import com.example.bookshelfapp.ui.components.BookImage
import com.example.bookshelfapp.ui.components.ErrorScreen
import com.example.bookshelfapp.ui.components.LoadingScreen

@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel,
    detailUiState: DetailUiState,
    modifier: Modifier = Modifier
) {
    when (detailUiState) {
        is DetailUiState.Success -> DetailInfoBook(detailUiState.book.volumeInfo)
        is DetailUiState.Loading -> LoadingScreen()
        is DetailUiState.Error -> ErrorScreen(onRepeat = { detailViewModel.repeat() })
    }

}

@Composable
fun DetailInfoBook(
    volumeInfo: VolumeInfo,
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
            BookImage(image = volumeInfo.imageLinks)
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = volumeInfo.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 5
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
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.description),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = volumeInfo.description ?: stringResource(id = R.string.no_description),
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.authors),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = volumeInfo.authors?.joinToString()
                ?: stringResource(id = R.string.authors_not_specified),
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        ButtonPreview(
            context = context,
            volumeInfo = volumeInfo
        )

    }
}

@Composable
fun ButtonPreview(
    context: Context,
    volumeInfo: VolumeInfo,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {
            val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(volumeInfo.previewLink))
            context.startActivity(urlIntent)
        }
    ) {
        Text(text = stringResource(id = R.string.preview))
    }

}
