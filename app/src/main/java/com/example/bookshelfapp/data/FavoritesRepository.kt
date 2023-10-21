package com.example.bookshelfapp.data

import com.example.bookshelfapp.data.local.entities.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getAll(): Flow<List<Favorite>>
}

