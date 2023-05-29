package com.example.bookshelfapp.ui.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelfapp.R
import com.example.bookshelfapp.model.ImageLink

@Composable
fun BookImage(imageUrl: String, modifier: Modifier = Modifier) {
    AsyncImage(
        modifier = Modifier
            .fillMaxHeight()
            .width(120.dp),
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(imageUrl.replace("http", "https"))
            .crossfade(true)
            .build(),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(id = R.drawable.loading_img),
        error = painterResource(id = R.drawable.ic_broken_image),
        contentDescription = null
    )
}