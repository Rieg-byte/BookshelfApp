package com.example.bookshelfapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookshelfapp.R
import com.example.bookshelfapp.ui.icons.BookshelfIcons

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
        modifier = modifier.fillMaxWidth().padding(8.dp),
        value = userInput,
        singleLine = true,
        onValueChange = updateUserInput,
        shape = RoundedCornerShape(50.dp),
        placeholder = {
            Text(text = stringResource(id = R.string.search))
        },
        textStyle = TextStyle(fontSize = 16.sp),
        leadingIcon = {
            Icon(
                imageVector = BookshelfIcons.Search,
                contentDescription = stringResource(id = R.string.search)
            )
        },
        trailingIcon = {
            if (userInput.isNotBlank()){
                IconButton(onClick = clearUserInput){
                    Icon(imageVector = BookshelfIcons.Close, contentDescription = "")
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
