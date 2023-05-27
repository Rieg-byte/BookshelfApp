package com.example.bookshelfapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bookshelfapp.ui.screens.detail.DetailScreen
import com.example.bookshelfapp.ui.screens.detail.DetailUiState
import com.example.bookshelfapp.ui.screens.detail.DetailViewModel
import com.example.bookshelfapp.ui.screens.home.SearchScreen
import com.example.bookshelfapp.ui.screens.home.SearchUiState
import com.example.bookshelfapp.ui.screens.home.SearchViewModel


@Composable
fun BookshelfNavHost(
    navController: NavHostController,
    searchUiState: SearchUiState,
    searchViewModel: SearchViewModel,
    detailViewModel: DetailViewModel,
    detailUiState: DetailUiState,
    navigateToDetailScreen: () -> Unit,
    startDestination: String
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Screens.Search.name) {
            SearchScreen(
                searchUiState = searchUiState,
                searchViewModel = searchViewModel,
                detailViewModel = detailViewModel,
                navigateToDetailScreen = navigateToDetailScreen
            )
        }
        composable(route = Screens.Detail.name) {
            DetailScreen(
                detailViewModel = detailViewModel,
                detailUiState = detailUiState
            )
        }

    }

}