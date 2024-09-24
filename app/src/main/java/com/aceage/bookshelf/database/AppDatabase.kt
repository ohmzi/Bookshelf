package com.aceage.bookshelf.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [FavoriteBook::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract fun favoriteBookDao(): FavoriteBookDao
}