package com.aceage.bookshelf.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.aceage.bookshelf.ui.viewmodels.SearchViewModel

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {
  Column(modifier = Modifier.padding(16.dp)) {

    Spacer(modifier = Modifier.height(32.dp))
    Text(modifier = Modifier.fillMaxWidth(), text = "Bookshelf \uD83D\uDCDA", style = MaterialTheme.typography.displayMedium, textAlign = TextAlign.Center)
    Spacer(modifier = Modifier.height(16.dp))

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = viewModel.searchQuery,
        placeholder = { Text("Search books") },
        onValueChange = { newQuery -> viewModel.onSearchQueryChange(newQuery) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { viewModel.onSearch(viewModel.searchQuery) })
    )

    if (!viewModel.books.isNullOrEmpty()) {
      Column(modifier = Modifier
          .weight(1f)
          .verticalScroll(rememberScrollState())) {

        for (book in viewModel.books!!) {
          Row(modifier = Modifier
              .fillMaxWidth()
              .padding(8.dp),
              verticalAlignment = Alignment.CenterVertically
          ) {
            if (book.cover_i != null) {
              AsyncImage(modifier = Modifier
                  .size(width = 50.dp, height = 75.dp)
                  .clip(RoundedCornerShape(4.dp)),
                  model = "https://covers.openlibrary.org/b/id/" + book.cover_i + "-M.jpg",
                  contentDescription = null,
                  contentScale = ContentScale.Fit
              )
            } else {
              Box(modifier = Modifier
                  .size(width = 50.dp, height = 75.dp)
                  .background(color = Color(0x33000000))
                  .clip(shape = RoundedCornerShape(4.dp))
              )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
              Text(text = book.title)
              if (book.author_name != null) {
                Text(text = book.author_name.first())
              }
              if (book.first_publish_year != null) {
                Text(text = book.first_publish_year.toString())
              }
            }
            Spacer(modifier = Modifier.width(16.dp))
            val isOnBookshelf = viewModel.booksOnBookshelf.any { it.id == book.key }
            Image(
                modifier = Modifier.clickable(enabled = !isOnBookshelf) {
                  viewModel.onAddToBookshelf(book)
                },
                imageVector = if (isOnBookshelf) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
          }
          Spacer(modifier = Modifier.height(16.dp))
        }
      }
    } else {
      Box(modifier = Modifier
          .fillMaxWidth()
          .weight(1f),
          contentAlignment = Alignment.Center
      ) {
        Text(text = "Search for your favorite books! :)", color = Color(0xAA000000))
      }
    }
  }
}