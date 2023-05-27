package com.example.bookshelfapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.bookshelfapp.R

/**
 * Поле поиска
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
    userInput: String,
    updateUserInput: (String) -> Unit,
    clearUserInput: () -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier
){
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = userInput,
        singleLine = true,
        onValueChange = updateUserInput,
        placeholder = {
            Text(text = stringResource(id = R.string.search))
        },
        textStyle = MaterialTheme.typography.titleSmall,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search)
            )
        },
        trailingIcon = {
            if (userInput.isNotBlank()){
                IconButton(onClick = clearUserInput){
                    Icon(imageVector = Icons.Default.Close, contentDescription = "")
                }
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus()
                onDone()
            }
        )
    )
}
