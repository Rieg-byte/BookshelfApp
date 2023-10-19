package com.example.bookshelfapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bookshelfapp.data.local.dao.FavoriteDao
import com.example.bookshelfapp.data.local.entities.Favorite

@Database(entities = [Favorite::class], version = 1)
abstract class BookshelfDatabase: RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    companion object {
        @Volatile
        private var Instance: BookshelfDatabase? = null
        fun getDatabase(context: Context): BookshelfDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, BookshelfDatabase::class.java, "bookshelf_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}