package com.example.bookshelfapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookshelfapp.ui.icons.BookshelfIcons

@Composable
fun BookCard(
    title: String,
    author: String,
    imageUrl: String,
    navigateToDetailScreen: () -> Unit = {},
    onDeleteBook: () -> Unit = {},
    showButtonDelete: Boolean = false,
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
                if (showButtonDelete) {
                    IconButton(onClick = onDeleteBook) {
                        Icon(imageVector = BookshelfIcons.DeleteOutline, contentDescription = "")
                    }
                }
            }
        }
    }
}
