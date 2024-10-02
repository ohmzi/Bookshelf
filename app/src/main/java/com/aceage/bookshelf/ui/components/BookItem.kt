package com.aceage.bookshelf.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aceage.bookshelf.database.FavoriteBook
import com.aceage.bookshelf.network.models.Book

@Composable
fun BookItem(
    book: Any,
    isOnBookshelf: Boolean,
    onAddToBookshelf: () -> Unit,
    onRemoveFromBookshelf: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val coverUrl = when (book) {
            is Book -> if (book.cover_i != null) "https://covers.openlibrary.org/b/id/${book.cover_i}-M.jpg" else null
            is FavoriteBook -> if (book.coverId != null) "https://covers.openlibrary.org/b/id/${book.coverId}-M.jpg" else null
            else -> null
        }

        if (coverUrl != null) {
            AsyncImage(
                modifier = Modifier
                    .size(width = 50.dp, height = 75.dp)
                    .clip(RoundedCornerShape(4.dp)),
                model = coverUrl,
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
            Text(
                text = when (book) {
                    is Book -> book.title
                    is FavoriteBook -> book.title
                    else -> ""
                },
                style = MaterialTheme.typography.bodyLarge
            )
            when (book) {
                is Book -> {
                    book.author_name?.firstOrNull()?.let { author ->
                        Text(text = author, style = MaterialTheme.typography.bodyMedium)
                    }
                    book.first_publish_year?.let { year ->
                        Text(text = year.toString(), style = MaterialTheme.typography.bodySmall)
                    }
                }

                is FavoriteBook -> {
                    book.author?.let { author ->
                        Text(text = author, style = MaterialTheme.typography.bodyMedium)
                    }
                    book.firstPublishYear?.let { year ->
                        Text(text = year.toString(), style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            imageVector = if (isOnBookshelf) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = if (isOnBookshelf) "Remove from bookshelf" else "Add to bookshelf",
            modifier = Modifier.clickable {
                if (isOnBookshelf) onRemoveFromBookshelf() else onAddToBookshelf()
            },
            tint = if (isOnBookshelf) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    }
}