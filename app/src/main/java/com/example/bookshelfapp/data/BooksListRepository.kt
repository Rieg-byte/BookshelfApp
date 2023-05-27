package com.example.bookshelfapp.data

import com.example.bookshelfapp.model.Book
import com.example.bookshelfapp.model.SearchResponse
import com.example.bookshelfapp.network.BookshelfApiService

interface BooksListRepository {
    suspend fun getBooksList(query: String): SearchResponse
    suspend fun getBookInfo(path: String): Book
}

class NetworkBooksListRepository(
    private val bookshelfApiService: BookshelfApiService
): BooksListRepository {
    override suspend fun getBooksList(query: String): SearchResponse {
        return bookshelfApiService.getSearchResponse(query)
    }

    override suspend fun getBookInfo(path: String): Book {
        return bookshelfApiService.getBookInfo(path)
    }
}