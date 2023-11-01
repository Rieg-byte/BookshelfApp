package com.example.bookshelfapp.di

import android.content.Context
import com.example.bookshelfapp.data.local.BooksDatabase
import com.example.bookshelfapp.data.repository.BooksRepository
import com.example.bookshelfapp.data.repository.BooksRepositoryImpl
import com.example.bookshelfapp.data.repository.FavoritesRepository
import com.example.bookshelfapp.data.repository.FavoritesRepositoryImpl
import com.example.bookshelfapp.data.local.FavoritesLocalDataSource
import com.example.bookshelfapp.data.remote.BooksApi
import com.example.bookshelfapp.data.remote.BooksRemoteDataSource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val booksRepository: BooksRepository
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

    private val retrofitService: BooksApi by lazy {
        retrofit.create(BooksApi::class.java)
    }

    private val booksRemoteDataSource by lazy {
        BooksRemoteDataSource(retrofitService)
    }

    private val favoritesLocalDataSource by lazy {
        FavoritesLocalDataSource(BooksDatabase.getDatabase(context).favoriteDao())
    }
    override val booksRepository: BooksRepository by lazy {
        BooksRepositoryImpl(booksRemoteDataSource)
    }

    override val favoritesRepository: FavoritesRepository by lazy {
        FavoritesRepositoryImpl(favoritesLocalDataSource)
    }
}

