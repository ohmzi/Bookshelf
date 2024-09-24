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
import com.aceage.bookshelf.ui.viewmodels.SharedViewModel

@Composable
fun BookshelfScreen(viewModel: SharedViewModel) {
  Column(modifier = Modifier.padding(16.dp)) {

    Spacer(modifier = Modifier.height(32.dp))
    Text(modifier = Modifier.fillMaxWidth(), text = "Bookshelf \uD83D\uDCDA", style = MaterialTheme.typography.displayMedium, textAlign = TextAlign.Center)
    Spacer(modifier = Modifier.height(16.dp))


    if (viewModel.booksOnBookshelf.isNotEmpty()) {
      Column(modifier = Modifier
          .weight(1f)
          .verticalScroll(rememberScrollState())) {

        for (book in viewModel.booksOnBookshelf) {
          Row(modifier = Modifier
              .fillMaxWidth()
              .padding(8.dp),
              verticalAlignment = Alignment.CenterVertically
          ) {
            if (book.coverId != null) {
              AsyncImage(modifier = Modifier
                  .size(width = 50.dp, height = 75.dp)
                  .clip(RoundedCornerShape(4.dp)),
                  model = "https://covers.openlibrary.org/b/id/" + book.coverId + "-M.jpg",
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
              if (book.author != null) {
                Text(text = book.author)
              }
              if (book.firstPublishYear != null) {
                Text(text = book.firstPublishYear.toString())
              }
            }
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
        Text(text = "Bookshelf is empty, go find some books! :)", color = Color(0xAA000000))
      }
    }
  }
}