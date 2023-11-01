package com.example.bookshelfapp.data.repository

import com.example.bookshelfapp.data.remote.BooksRemoteDataSource
import com.example.bookshelfapp.data.remote.model.Book
import com.example.bookshelfapp.data.remote.model.BookList

class BooksRepositoryImpl(
    private val booksRemoteDataSource: BooksRemoteDataSource,
): BooksRepository {
    override suspend fun getBooksList(query: String): BookList = booksRemoteDataSource.getBookList(query)

    override suspend fun getBookInfo(path: String): Book = booksRemoteDataSource.getBookInfo(path)
}