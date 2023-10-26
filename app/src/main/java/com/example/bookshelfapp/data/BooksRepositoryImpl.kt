package com.example.bookshelfapp.data

import com.example.bookshelfapp.data.remote.BooksRemoteDataSource
import com.example.bookshelfapp.data.remote.model.BookInfo
import com.example.bookshelfapp.data.remote.model.Item

class BooksRepositoryImpl(
    private val booksRemoteDataSource: BooksRemoteDataSource,
): BooksRepository {
    override suspend fun getBooksList(query: String): List<Item> = booksRemoteDataSource.getBookList(query).convertToItemList()

    override suspend fun getBookInfo(path: String): BookInfo = booksRemoteDataSource.getBookInfo(path).convertToBookInfo()
}