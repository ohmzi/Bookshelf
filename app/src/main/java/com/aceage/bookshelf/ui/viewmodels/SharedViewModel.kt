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
class SharedViewModel @Inject constructor(
    private val booksRepository: IBooksRepository
) : ViewModel() {

    var searchQuery by mutableStateOf("")
    var books by mutableStateOf<List<Book>>(emptyList())
    var booksOnBookshelf by mutableStateOf(emptyList<FavoriteBook>())
    private var searchJob: Job? = null
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var currentPage by mutableStateOf(1)
    private val booksPerPage = 20
    private var allSearchResults = listOf<Book>()

    init {
        viewModelScope.launch {
            booksOnBookshelf = booksRepository.getAllOnBookshelf()
        }
    }

    fun onSearchQueryChange(query: String) {
        searchQuery = query
        currentPage = 1
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300) // Debounce
            if (query.isNotEmpty()) {
                performSearch(query)
            } else {
                books = emptyList()
                errorMessage = null
            }
        }
    }

    fun onSearch(query: String) {
        viewModelScope.launch {
            currentPage = 1
            performSearch(query)
        }
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            val result = booksRepository.searchBooks(query)
            isLoading = false
            if (result != null) {
                allSearchResults = result
                books = allSearchResults.take(booksPerPage)
                errorMessage = null
            } else {
                books = emptyList()
                errorMessage = "An error occurred while searching for books. Please try again."
            }
        }
    }

    fun loadNextPage() {
        if (currentPage * booksPerPage < allSearchResults.size) {
            currentPage++
            books = allSearchResults.take(currentPage * booksPerPage)
        }
    }

    fun onAddToBookshelf(book: Book) {
        viewModelScope.launch {
            booksRepository.addToBookshelf(book)
            booksOnBookshelf = booksRepository.getAllOnBookshelf()
        }
    }

    fun onRemoveFromBookshelf(book: FavoriteBook) {
        viewModelScope.launch {
            booksRepository.removeFromBookshelf(book)
            booksOnBookshelf = booksRepository.getAllOnBookshelf()
        }
    }
}