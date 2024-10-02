package com.aceage.bookshelf

import BookshelfScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aceage.bookshelf.ui.screens.SearchScreen
import com.aceage.bookshelf.ui.theme.BookshelfTheme
import com.aceage.bookshelf.ui.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

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
                    val currentRoute =
                        navController.currentBackStackEntryAsState().value?.destination?.route
                    NavigationBar {
                        NavigationBarItem(
                            selected = currentRoute == "search",
                            icon = {
                                Image(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = null
                                )
                            },
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
                            icon = {
                                Image(
                                    imageVector = Icons.Filled.Favorite,
                                    contentDescription = null
                                )
                            },
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