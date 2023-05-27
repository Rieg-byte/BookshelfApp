package com.example.bookshelfapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.bookshelfapp.R

/**
 * Показывает сообщение о том, что ничего не найдено
 */
@Composable
fun NotFoundScreen(
    userInput: String,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(textAlign = TextAlign.Center, text = buildAnnotatedString {
            append(stringResource(id = R.string.not_found))
            append(' ')
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                append(userInput)
            }
        })
    }
}