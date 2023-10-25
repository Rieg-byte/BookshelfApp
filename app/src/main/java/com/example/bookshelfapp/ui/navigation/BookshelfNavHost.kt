package com.example.bookshelfapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.bookshelfapp.ui.navigation.destinations.BottomBarDestination
import com.example.bookshelfapp.ui.navigation.destinations.FavoriteDestination
import com.example.bookshelfapp.ui.navigation.destinations.SearchDestination
import com.example.bookshelfapp.ui.screens.detail.DetailScreen
import com.example.bookshelfapp.ui.screens.favorites.FavoritesScreen
import com.example.bookshelfapp.ui.screens.search.SearchScreen


@Composable
fun BookshelfNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = BottomBarDestination.FAVORITES.name) {
        favoriteGraph(navController = navController)
        searchGraph(navController = navController)
    }
}

/**
 * Вложенный граф поиска
 */
fun NavGraphBuilder.searchGraph(navController: NavController) {
    navigation(
        startDestination = SearchDestination.SEARCH_SCREEN.name,
        route = BottomBarDestination.SEARCH.name
    ) {
        composable(SearchDestination.SEARCH_SCREEN.name) {
            SearchScreen(
                navigateToDetailScreen = { bookId ->
                    navController.navigate("${SearchDestination.DETAIL_SCREEN.name}/$bookId") {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(
            route = "${SearchDestination.DETAIL_SCREEN.name}/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.StringType })
        ) {
            DetailScreen(navigateUp = {navController.navigateUp()})
        }
    }
}

fun NavGraphBuilder.favoriteGraph(navController: NavController) {
    navigation(
        startDestination = FavoriteDestination.FAVORITE_SCREEN.name,
        route = BottomBarDestination.FAVORITES.name
    ) {
        composable(FavoriteDestination.FAVORITE_SCREEN.name) {
            FavoritesScreen(
                navigateToDetailScreen = { bookId ->
                    navController.navigate("${FavoriteDestination.DETAIL_SCREEN.name}/$bookId") {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(
            route = "${FavoriteDestination.DETAIL_SCREEN.name}/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.StringType })
        ) {
            DetailScreen(navigateUp = {navController.navigateUp()})
        }
    }
}