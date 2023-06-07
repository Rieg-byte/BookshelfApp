package com.example.bookshelfapp.ui.navigation.destinations

import androidx.annotation.StringRes

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.bookshelfapp.R
import com.example.bookshelfapp.ui.icons.BookshelfIcons

enum class BottomBarDestination(@StringRes val label: Int, val icon: ImageVector){
    BOOKS(label = R.string.books, icon = BookshelfIcons.LibraryBooks),
    FAVORITES(label = R.string.favorites, icon = BookshelfIcons.Bookmarks),
    SEARCH(label = R.string.search, icon = BookshelfIcons.Search)
}
