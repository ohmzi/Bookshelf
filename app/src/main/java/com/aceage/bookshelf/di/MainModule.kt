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
class MainModule {

  @Provides
  @Singleton
  fun providesRetrofit(): Retrofit = Retrofit.Builder()
      .baseUrl("https://openlibrary.org/")
      .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
      .build()

  @Provides
  @Singleton
  fun providesOpenLibraryApi(retrofit: Retrofit): OpenLibraryApi =
      retrofit.create(OpenLibraryApi::class.java)

  @Provides
  @Singleton
  fun provideBooksRepository(openLibraryApi: OpenLibraryApi, favoriteBookDao: FavoriteBookDao): IBooksRepository = BooksRepository(openLibraryApi, favoriteBookDao)

  @Provides
  @Singleton
  fun providesAppDatabase(
      @ApplicationContext applicationContext: Context
  ): AppDatabase = Room.databaseBuilder(
      applicationContext,
      AppDatabase::class.java,
      "app-db"
  ).build()

  @Provides
  @Singleton
  fun providesFavoriteBookDao(appDatabase: AppDatabase) = appDatabase.favoriteBookDao()


}