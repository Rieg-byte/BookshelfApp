package com.example.bookshelfapp.data.local

import com.example.bookshelfapp.data.local.dao.FavoriteDao
import com.example.bookshelfapp.data.local.entities.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getAll(): Flow<List<Favorite>>
}

class OfflineFavoritesRepository(private val favoriteDao: FavoriteDao): FavoritesRepository{
    override fun getAll(): Flow<List<Favorite>> = favoriteDao.getAll()

}