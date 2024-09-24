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
import com.aceage.bookshelf.ui.components.BookItem
import com.aceage.bookshelf.ui.viewmodels.SharedViewModel

@Composable
fun BookshelfScreen(viewModel: SharedViewModel) {
  Column(modifier = Modifier.padding(16.dp)) {

    Spacer(modifier = Modifier.height(32.dp))
    Text(modifier = Modifier.fillMaxWidth(), text = "Bookshelf \uD83D\uDCDA", style = MaterialTheme.typography.displayMedium, textAlign = TextAlign.Center)
    Spacer(modifier = Modifier.height(16.dp))


    if (viewModel.booksOnBookshelf.isNotEmpty()) {
        LazyColumn {
            items(viewModel.booksOnBookshelf) { book ->
                BookItem(
                    book = book,
                    isOnBookshelf = true,
                    onAddToBookshelf = {},
                    onRemoveFromBookshelf = { viewModel.onRemoveFromBookshelf(book) }
                )
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "Your bookshelf is empty. Add some books from the search screen!",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                textAlign = TextAlign.Center
            )
        }
    }
  }
}