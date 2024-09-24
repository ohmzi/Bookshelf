package com.aceage.bookshelf.network.repos

import android.util.Log
import com.aceage.bookshelf.database.FavoriteBook
import com.aceage.bookshelf.database.FavoriteBookDao
import com.aceage.bookshelf.network.OpenLibraryApi
import com.aceage.bookshelf.network.models.Book
import javax.inject.Inject


class BooksRepository @Inject constructor(
    private val booksApi: OpenLibraryApi,
    private val favoriteBookDao: FavoriteBookDao
) : IBooksRepository {

  override suspend fun searchBooks(query: String): List<Book>? {
    Log.i("BooksRepository", "searchBooks with query: $query")
    val response = booksApi.searchBooks(query)
    return if (response.isSuccessful) {
      Log.i("BooksRepository", "searchBooks: ${response.body()!!.docs}")
      response.body()!!.docs
    } else {
      // error
      Log.i("BooksRepository", "searchBooks failed ${response.errorBody()!!.string()}")
      null
    }
  }

  override suspend fun getAllOnBookshelf(): List<FavoriteBook> = favoriteBookDao.getAll()

  override suspend fun addToBookshelf(book: Book) {
    favoriteBookDao.insert(FavoriteBook(
        id = book.key,
        title = book.title,
        author = book.author_name?.get(0),
        coverId = book.cover_i,
        firstPublishYear = book.first_publish_year
    ))
  }

  override suspend fun removeFromBookshelf(book: FavoriteBook) {
    favoriteBookDao.delete(book)
  }
}