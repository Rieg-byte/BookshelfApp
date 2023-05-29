package com.example.bookshelfapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.bookshelfapp.ui.screens.detail.DetailScreen
import com.example.bookshelfapp.ui.screens.search.SearchScreen
import com.example.bookshelfapp.ui.screens.search.SearchViewModel


@Composable
fun BookshelfNavHost(
    navController: NavHostController,
    searchViewModel: SearchViewModel
) {
    NavHost(navController = navController, startDestination = Screens.Search.name) {
        composable(route = Screens.Search.name) {
            SearchScreen(
                searchViewModel = searchViewModel,
                navigateToDetailScreen = {bookId -> navController.navigate("${Screens.Detail.name}/$bookId")}
            )
        }
        composable(
            route = "${Screens.Detail.name}/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.StringType })
        ) {
            DetailScreen()
        }

    }

}