package com.example.bookshelfapp.data.repository


import com.example.bookshelfapp.data.remote.model.Book
import com.example.bookshelfapp.data.remote.model.BookList

interface BooksRepository {
    suspend fun getBooksList(query: String): BookList
    suspend fun getBookInfo(path: String): Book
}