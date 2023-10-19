package com.example.bookshelfapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.bookshelfapp.data.local.entities.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    fun getAll(): Flow<List<Favorite>>
}