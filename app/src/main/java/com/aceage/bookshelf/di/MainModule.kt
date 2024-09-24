package com.aceage.bookshelf.di

import android.content.Context
import androidx.room.Room
import com.aceage.bookshelf.database.AppDatabase
import com.aceage.bookshelf.database.FavoriteBookDao
import com.aceage.bookshelf.network.OpenLibraryApi
import com.aceage.bookshelf.network.repos.BooksRepository
import com.aceage.bookshelf.network.repos.IBooksRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase { return Room.databaseBuilder(context, AppDatabase::class.java, "bookshelf_database").build() }

    @Provides
    fun provideFavoriteBookDao(database: AppDatabase): FavoriteBookDao {
        return database.favoriteBookDao()
    }

    @Provides
    @Singleton
    fun provideBooksRepository(booksRepository: BooksRepository): IBooksRepository {
        return booksRepository
    }
}