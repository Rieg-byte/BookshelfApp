package com.example.bookshelfapp.di

import com.example.bookshelfapp.data.BooksListRepository
import com.example.bookshelfapp.data.NetworkBooksListRepository
import com.example.bookshelfapp.network.BookshelfApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val booksListRepository: BooksListRepository
}

class DefaultAppContainer(): AppContainer {
    private val BASE_URL = "https://www.googleapis.com/books/v1/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    private val retrofitService: BookshelfApiService by lazy {
        retrofit.create(BookshelfApiService::class.java)
    }

    override val booksListRepository: BooksListRepository by lazy {
        NetworkBooksListRepository(retrofitService)
    }
}

