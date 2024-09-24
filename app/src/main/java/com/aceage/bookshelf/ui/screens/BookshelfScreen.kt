import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aceage.bookshelf.ui.components.BookItem
import com.aceage.bookshelf.ui.viewmodels.SharedViewModel

@Composable
fun BookshelfScreen(viewModel: SharedViewModel) {
    val booksPerPage = 20
    var currentPage by remember { mutableStateOf(1) }
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

        if (viewModel.booksOnBookshelf.isNotEmpty()) {
            LazyColumn(state = listState) {
                items(viewModel.booksOnBookshelf.take(currentPage * booksPerPage)) { book ->
                    BookItem(
                        book = book,
                        isOnBookshelf = true,
                        onAddToBookshelf = {},
                        onRemoveFromBookshelf = { viewModel.onRemoveFromBookshelf(book) }
                    )
                }

                item {
                    if (currentPage * booksPerPage < viewModel.booksOnBookshelf.size) {
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
                        if (lastIndex != null && lastIndex >= (currentPage * booksPerPage) - 5) {
                            currentPage++
                        }
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