package com.example.bookshelfapp.data

import com.example.bookshelfapp.data.local.entities.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getAll(): Flow<List<Favorite>>
    suspend fun insertBook(favorite: Favorite)
    suspend fun deleteBook(favorite: Favorite)
    fun isFavoriteBook(bookId: String): Flow<Boolean>
}

