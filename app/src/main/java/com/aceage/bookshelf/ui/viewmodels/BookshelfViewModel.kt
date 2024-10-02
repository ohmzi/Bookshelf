package com.aceage.bookshelf.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aceage.bookshelf.database.FavoriteBook
import com.aceage.bookshelf.network.repos.IBooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookshelfViewModel @Inject constructor(
    private val booksRepository: IBooksRepository
) : ViewModel() {


    var books by mutableStateOf(emptyList<FavoriteBook>())

    init {
        viewModelScope.launch {
            books = booksRepository.getAllOnBookshelf()
        }
    }
}