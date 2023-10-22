package com.example.bookshelfapp.data.remote

import com.example.bookshelfapp.data.remote.model.Book
import com.example.bookshelfapp.data.remote.model.BookList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksApi {
    @GET("volumes")
    suspend fun getBookList(@Query("q") query: String): BookList

    @GET("volumes/{volume_id}")
    suspend fun getBookInfo(@Path("volume_id") volumeId: String): Book
}