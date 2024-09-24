package com.aceage.bookshelf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.aceage.bookshelf.ui.screens.BookshelfScreen
import com.aceage.bookshelf.ui.screens.SearchScreen
import com.aceage.bookshelf.ui.theme.BookshelfTheme
import com.aceage.bookshelf.ui.viewmodels.SharedViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      BookshelfTheme {
        val navController = rememberNavController()
          val sharedViewModel: SharedViewModel = hiltViewModel()
          Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
          val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
          NavigationBar {
            NavigationBarItem(
                selected = currentRoute == "search",
                icon = { Image(imageVector = Icons.Filled.Search, contentDescription = null) },
                label = { Text("Search") },
                onClick = {
                    navController.navigate("search") {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
            NavigationBarItem(
                selected = currentRoute == "bookshelf",
                icon = { Image(imageVector = Icons.Filled.Favorite, contentDescription = null) },
                label = { Text("Bookshelf") },
                onClick = {
                    navController.navigate("bookshelf") {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
          }
        }) { p ->
          NavHost(navController, startDestination = "search") {
            composable("search") {
              SearchScreen(viewModel = sharedViewModel)
            }
            composable("bookshelf") {
              BookshelfScreen(viewModel = sharedViewModel)
            }
          }
        }
      }
    }
  }
}