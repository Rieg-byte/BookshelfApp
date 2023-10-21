package com.example.bookshelfapp.data.remote

import com.example.bookshelfapp.model.Book
import com.example.bookshelfapp.model.BookListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookshelfApiService {
    @GET("volumes")
    suspend fun getBookListResponse(@Query("q") query: String): BookListResponse

    @GET("volumes/{volume_id}")
    suspend fun getBookInfo(@Path("volume_id") volumeId: String): Book
}