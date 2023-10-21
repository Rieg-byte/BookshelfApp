package com.example.bookshelfapp.data

import com.example.bookshelfapp.data.local.dao.FavoriteDao
import com.example.bookshelfapp.data.local.entities.Favorite
import kotlinx.coroutines.flow.Flow

class FavoritesRepositoryImpl(private val favoritesLocalDataSource: FavoriteDao) : FavoritesRepository {
    override fun getAll(): Flow<List<Favorite>> = favoritesLocalDataSource.getAll()
}