package com.example.bookshelfapp.data

import com.example.bookshelfapp.data.local.dao.FavoriteDao
import com.example.bookshelfapp.data.local.entities.Favorite
import com.example.bookshelfapp.model.Book
import com.example.bookshelfapp.model.BookListResponse
import com.example.bookshelfapp.data.remote.BookshelfApiService
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    suspend fun getBooksListResponse(query: String): BookListResponse
    suspend fun getBookInfo(path: String): Book
    fun getAll(): Flow<List<Favorite>>
}

class NetworkBooksRepository(
    private val bookshelfApiService: BookshelfApiService,
    private val favoriteDao: FavoriteDao
): BooksRepository {
    override suspend fun getBooksListResponse(query: String): BookListResponse {
        return bookshelfApiService.getBookListResponse(query)
    }

    override suspend fun getBookInfo(path: String): Book {
        return bookshelfApiService.getBookInfo(path)
    }
    override fun getAll(): Flow<List<Favorite>> = favoriteDao.getAll()
}