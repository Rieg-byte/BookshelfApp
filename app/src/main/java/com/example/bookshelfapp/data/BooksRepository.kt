package com.example.bookshelfapp.data


import com.example.bookshelfapp.model.Book
import com.example.bookshelfapp.model.BookListResponse

interface BooksRepository {
    suspend fun getBooksListResponse(query: String): BookListResponse
    suspend fun getBookInfo(path: String): Book
}