package com.example.bookshelfapp.data


import com.example.bookshelfapp.data.remote.model.BookInfo
import com.example.bookshelfapp.data.remote.model.Item

interface BooksRepository {
    suspend fun getBooksList(query: String): List<Item>
    suspend fun getBookInfo(path: String): BookInfo
}