package com.aceage.bookshelf.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aceage.bookshelf.ui.components.BookItem
import com.aceage.bookshelf.ui.viewmodels.SharedViewModel

@Composable
fun SearchScreen(viewModel: SharedViewModel) {
    val listState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        SearchHeader()
        SearchTextField(viewModel)
        SearchContent(viewModel, listState)
    }

    LoadNextPageEffect(viewModel, listState)
}

@Composable
private fun SearchHeader() {
    Spacer(modifier = Modifier.height(32.dp))
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Bookshelf 📚",
        style = MaterialTheme.typography.displayMedium,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun SearchTextField(viewModel: SharedViewModel) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = viewModel.searchQuery,
        placeholder = { Text("Search books") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
        onValueChange = viewModel::onSearchQueryChange,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { viewModel.onSearch(viewModel.searchQuery) }),
        singleLine = true
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun SearchContent(viewModel: SharedViewModel, listState: LazyListState) {
    when {
        viewModel.isLoading -> LoadingIndicator()
        viewModel.errorMessage != null -> ErrorMessage(viewModel.errorMessage!!)
        viewModel.books.isEmpty() -> EmptySearchResult(viewModel.searchQuery)
        else -> BookList(viewModel, listState)
    }
}

@Composable
private fun LoadingIndicator() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorMessage(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun EmptySearchResult(searchQuery: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = if (searchQuery.isEmpty()) "Search for your favorite books! :)"
            else "No books found. Try a different search term.",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun BookList(viewModel: SharedViewModel, listState: LazyListState) {
    LazyColumn(state = listState) {
        items(viewModel.books) { book ->
            BookItem(
                book = book,
                isOnBookshelf = viewModel.booksOnBookshelf.any { it.id == book.key },
                onAddToBookshelf = { viewModel.onAddToBookshelf(book) },
                onRemoveFromBookshelf = { }
            )
        }

        item {
            if (viewModel.books.size % 20 == 0) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}