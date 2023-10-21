package com.example.bookshelfapp.di

import android.content.Context
import com.example.bookshelfapp.data.local.BookshelfDatabase
import com.example.bookshelfapp.data.BooksRepository
import com.example.bookshelfapp.data.NetworkBooksRepository
import com.example.bookshelfapp.data.remote.network.BookshelfApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val booksRepository: BooksRepository
}

class DefaultAppContainer(context: Context): AppContainer {
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

    override val booksRepository: BooksRepository by lazy {
        NetworkBooksRepository(retrofitService, BookshelfDatabase.getDatabase(context).favoriteDao())
    }

}

