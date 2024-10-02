package com.aceage.bookshelf.network.repos

import com.aceage.bookshelf.database.FavoriteBook
import com.aceage.bookshelf.network.models.Book


interface IBooksRepository {
    suspend fun searchBooks(query: String): List<Book>?
    suspend fun addToBookshelf(book: Book)
    suspend fun removeFromBookshelf(book: FavoriteBook)
    suspend fun getAllOnBookshelf(): List<FavoriteBook>
}