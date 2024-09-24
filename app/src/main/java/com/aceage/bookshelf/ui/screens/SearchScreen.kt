package com.aceage.bookshelf.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aceage.bookshelf.ui.viewmodels.SharedViewModel

@Composable
fun SearchScreen(viewModel: SharedViewModel) {
  Column(modifier = Modifier.padding(16.dp)) {

    Spacer(modifier = Modifier.height(32.dp))
    Text(modifier = Modifier.fillMaxWidth(), text = "Bookshelf \uD83D\uDCDA", style = MaterialTheme.typography.displayMedium, textAlign = TextAlign.Center)
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
            viewModel.books.isNullOrEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = if (viewModel.searchQuery.isEmpty()) "Search for your favorite books! :)"
                        else "No books found. Try a different search term.",
                        color = Color(0xAA000000),
                        textAlign = TextAlign.Center
                    )
                }
            }
            else -> {
                LazyColumn {
                    items(viewModel.books!!) { book ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (book.cover_i != null) {
                                AsyncImage(
                                    modifier = Modifier
                                        .size(width = 50.dp, height = 75.dp)
                                        .clip(RoundedCornerShape(4.dp)),
                                    model = "https://covers.openlibrary.org/b/id/${book.cover_i}-M.jpg",
                                    contentDescription = "Book cover",
                                    contentScale = ContentScale.Fit
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .size(width = 50.dp, height = 75.dp)
                                        .background(color = Color(0x33000000))
                                        .clip(shape = RoundedCornerShape(4.dp))
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = book.title, style = MaterialTheme.typography.bodyLarge)
                                book.author_name?.firstOrNull()?.let { author ->
                                    Text(text = author, style = MaterialTheme.typography.bodyMedium)
                                }
                                book.first_publish_year?.let { year ->
                                    Text(text = year.toString(), style = MaterialTheme.typography.bodySmall)
                                }
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            val isOnBookshelf = viewModel.booksOnBookshelf.any { it.id == book.key }
                            Icon(
                                imageVector = if (isOnBookshelf) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = if (isOnBookshelf) "Remove from bookshelf" else "Add to bookshelf",
                                modifier = Modifier.clickable(enabled = !isOnBookshelf) {
                                    viewModel.onAddToBookshelf(book)
                                },
                                tint = if (isOnBookshelf) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                        }                    }
                }
            }
        }
    }
}