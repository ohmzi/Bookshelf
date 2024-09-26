package com.aceage.bookshelf.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aceage.bookshelf.database.FavoriteBook
import com.aceage.bookshelf.network.models.Book
import com.aceage.bookshelf.network.repos.IBooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val booksRepository: IBooksRepository
) : ViewModel() {

    var searchQuery by mutableStateOf("")
    var books by mutableStateOf<List<Book>?>(emptyList())
    var booksOnBookshelf by mutableStateOf(emptyList<FavoriteBook>())
    private var searchJob: Job? = null

    fun onSearchQueryChange(query: String) {
        searchQuery = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(10)
            if (query.isNotEmpty()) {
                performSearch(query)
            } else {
                books = emptyList()
            }
        }
    }

    fun onSearch(query: String) {
        viewModelScope.launch {
            performSearch(query)
        }
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            books = booksRepository.searchBooks(query)
        }
    }

    fun onAddToBookshelf(book: Book) {
        viewModelScope.launch {
            booksRepository.addToBookshelf(book)
            booksOnBookshelf = booksRepository.getAllOnBookshelf()
        }
    }
}