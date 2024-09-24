package com.aceage.bookshelf.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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

    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Bookshelf 📚",
            style = MaterialTheme.typography.displayMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.searchQuery,
            placeholder = { Text("Search books") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            onValueChange = { viewModel.onSearchQueryChange(it) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { viewModel.onSearch(viewModel.searchQuery) }),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            viewModel.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            viewModel.errorMessage != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = viewModel.errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                }
            }
            viewModel.books.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = if (viewModel.searchQuery.isEmpty()) "Search for your favorite books! :)"
                        else "No books found. Try a different search term.",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        textAlign = TextAlign.Center
                    )
                }
            }
            else -> {
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

                LaunchedEffect(listState) {
                    snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                        .collect { lastIndex ->
                            if (lastIndex != null && lastIndex >= viewModel.books.size - 5) {
                                viewModel.loadNextPage()
                            }
                        }
                }
            }
        }
    }
}