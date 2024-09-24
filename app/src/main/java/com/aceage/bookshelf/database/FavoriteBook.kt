package com.aceage.bookshelf.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class FavoriteBook(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "author")
    val author: String?,
    @ColumnInfo(name = "cover_i")
    val coverId: Long?,
    @ColumnInfo(name = "first_publish_year")
    val firstPublishYear: Int?
)