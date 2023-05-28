package com.example.bookshelfapp.data

import com.example.bookshelfapp.model.Book
import com.example.bookshelfapp.model.BookListResponse
import com.example.bookshelfapp.network.BookshelfApiService

interface BooksListRepository {
    suspend fun getBooksListResponse(query: String): BookListResponse
    suspend fun getBookInfo(path: String): Book
}

class NetworkBooksListRepository(
    private val bookshelfApiService: BookshelfApiService
): BooksListRepository {
    override suspend fun getBooksListResponse(query: String): BookListResponse {
        return bookshelfApiService.getBookListResponse(query)
    }

    override suspend fun getBookInfo(path: String): Book {
        return bookshelfApiService.getBookInfo(path)
    }
}