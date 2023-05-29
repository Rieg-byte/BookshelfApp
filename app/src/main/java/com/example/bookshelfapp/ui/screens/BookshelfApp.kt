package com.example.bookshelfapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bookshelfapp.R
import com.example.bookshelfapp.navigation.BookshelfNavHost
import com.example.bookshelfapp.navigation.Screens
import com.example.bookshelfapp.ui.components.SearchField
import com.example.bookshelfapp.ui.screens.search.SearchViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfApp(modifier: Modifier = Modifier) {
    val searchViewModel:SearchViewModel = viewModel(factory = SearchViewModel.Factory)
    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route ?: Screens.Search.name

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            BookshelfAppBar(
                currentScreen = currentScreen,
                navigateUp = { navController.navigateUp() },
                canNavigateUp = navController.previousBackStackEntry != null,
                searchViewModel = searchViewModel
            )
        }
        ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colorScheme.background
        ) {
            BookshelfNavHost(
                navController = navController,
                searchViewModel = searchViewModel
            )
        }

    }
}


/**
 * Composable-функция, которая отображает верхнюю панель с поиском
 * и кнопку назад, если есть такая возможность.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfAppBar(
    navigateUp: () -> Unit,
    canNavigateUp: Boolean,
    currentScreen: String,
    searchViewModel: SearchViewModel,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = Modifier
            .height(70.dp)
            .padding(8.dp)
        ,
        title = {
            if (currentScreen == "Search") {
                SearchField(
                    userInput = searchViewModel.userInput,
                    updateUserInput = { searchViewModel.updateUserInput(it) },
                    clearUserInput = {
                        searchViewModel.clearUserInput()
                    },
                    onDone = { searchViewModel.getBooksList(searchViewModel.userInput) }
                )
            }
        },
        navigationIcon = {
            if (canNavigateUp) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button)
                    )
                }
            }
        }
    )
}