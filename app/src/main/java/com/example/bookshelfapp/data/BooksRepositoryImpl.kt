package com.example.bookshelfapp.data

import com.example.bookshelfapp.data.remote.BooksApi
import com.example.bookshelfapp.data.remote.model.BookInfo
import com.example.bookshelfapp.data.remote.model.Item

class BooksRepositoryImpl(
    private val booksRemoteDataSource: BooksApi,
): BooksRepository {
    override suspend fun getBooksList(query: String): List<Item> {
        val result = booksRemoteDataSource.getBookList(query).items
        return result?.map {
            Item(
                id = it.id,
                title = it.volumeInfo.title,
                imageUrl = it.volumeInfo.imageLinks?.smallThumbnail ?: "",
                author = it.volumeInfo.authors?.joinToString() ?: ""
            )
        } ?: emptyList()
    }

    override suspend fun getBookInfo(path: String): BookInfo {
        val result = booksRemoteDataSource.getBookInfo(path)
        return BookInfo(
            title = result.volumeInfo.title,
            description = result.volumeInfo.description ?: "",
            author = result.volumeInfo.authors?.joinToString() ?: "",
            imageUrl = result.volumeInfo.imageLinks?.smallThumbnail ?: "",
            previewLink = result.volumeInfo.previewLink ?: ""
        )
    }
}