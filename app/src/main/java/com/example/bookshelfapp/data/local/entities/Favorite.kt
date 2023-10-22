package com.example.bookshelfapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("favorites")
data class Favorite(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val author: String?,
    val title: String,
    @ColumnInfo("image_url") val imageUrl: String,
    @ColumnInfo("self_link") val selfLink: String
)
