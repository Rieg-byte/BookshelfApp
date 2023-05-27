package com.example.bookshelfapp.network

import com.example.bookshelfapp.model.Book
import com.example.bookshelfapp.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookshelfApiService {
    @GET("volumes")
    suspend fun getSearchResponse(@Query("q") query: String): SearchResponse

    @GET("volumes/{volume_id}")
    suspend fun getBookInfo(@Path("volume_id") volumeId: String): Book
}