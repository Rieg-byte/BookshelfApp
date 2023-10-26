package com.example.bookshelfapp.data.remote

import com.example.bookshelfapp.data.remote.model.Book
import com.example.bookshelfapp.data.remote.model.BookList

class BooksRemoteDataSource(private val booksApi: BooksApi) {
    suspend fun getBookList(query: String): BookList = booksApi.getBookList(query)
    suspend fun getBookInfo(volumeId: String): Book = booksApi.getBookInfo(volumeId)
}