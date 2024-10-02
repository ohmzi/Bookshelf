package com.aceage.bookshelf.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface FavoriteBookDao {
    @Query("SELECT * FROM favoritebook")
    suspend fun getAll(): List<FavoriteBook>

    @Upsert
    suspend fun insert(book: FavoriteBook)

    @Delete
    suspend fun delete(book: FavoriteBook)
}