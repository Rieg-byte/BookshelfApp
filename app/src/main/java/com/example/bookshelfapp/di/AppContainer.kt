package com.example.bookshelfapp.di

import com.example.bookshelfapp.data.BooksListRepository
import com.example.bookshelfapp.data.NetworkBooksListRepository
import com.example.bookshelfapp.network.BookshelfApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val booksListRepository: BooksListRepository
}

class DefaultAppContainer(): AppContainer {
    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()
    private val BASE_URL = "https://www.googleapis.com/books/v1/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    private val retrofitService: BookshelfApiService by lazy {
        retrofit.create(BookshelfApiService::class.java)
    }

    override val booksListRepository: BooksListRepository by lazy {
        NetworkBooksListRepository(retrofitService)
    }
}

