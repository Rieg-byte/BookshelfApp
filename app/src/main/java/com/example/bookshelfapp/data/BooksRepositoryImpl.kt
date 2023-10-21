package com.example.bookshelfapp.data

import com.example.bookshelfapp.data.remote.BooksApiService
import com.example.bookshelfapp.model.Book
import com.example.bookshelfapp.model.BookListResponse

class BooksRepositoryImpl(
    private val booksRemoteDataSource: BooksApiService,
): BooksRepository {
    override suspend fun getBooksListResponse(query: String): BookListResponse {
        return booksRemoteDataSource.getBookListResponse(query)
    }

    override suspend fun getBookInfo(path: String): Book {
        return booksRemoteDataSource.getBookInfo(path)
    }
}