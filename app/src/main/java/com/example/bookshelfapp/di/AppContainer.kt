package com.example.bookshelfapp.di

import android.content.Context
import com.example.bookshelfapp.data.local.BookshelfDatabase
import com.example.bookshelfapp.data.local.FavoritesRepository
import com.example.bookshelfapp.data.local.OfflineFavoritesRepository
import com.example.bookshelfapp.data.remote.BooksListRepository
import com.example.bookshelfapp.data.remote.NetworkBooksListRepository
import com.example.bookshelfapp.data.remote.network.BookshelfApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val booksListRepository: BooksListRepository
    val favoritesRepository: FavoritesRepository
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

    override val booksListRepository: BooksListRepository by lazy {
        NetworkBooksListRepository(retrofitService)
    }

    override val favoritesRepository: FavoritesRepository by lazy {
        OfflineFavoritesRepository(BookshelfDatabase.getDatabase(context).favoriteDao())
    }
}

