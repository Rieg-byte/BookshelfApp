package com.example.bookshelfapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bookshelfapp.ui.navigation.BookshelfNavHost
import com.example.bookshelfapp.ui.navigation.destinations.BottomBarDestination


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfApp(modifier: Modifier = Modifier) {
    val navController: NavHostController = rememberNavController()
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = { },
        bottomBar = { BookshelfBottomBar(navController = navController) }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colorScheme.background
        ) {
            BookshelfNavHost(
                navController = navController
            )
        }

    }
}


@Composable
fun BookshelfBottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    val items = listOf(
        BottomBarDestination.FAVORITES,
        BottomBarDestination.SEARCH
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    BottomNavigation(
        modifier = modifier,
        backgroundColor = MaterialTheme.colorScheme.background,
        contentColor = Color.White

    ) {
        items.forEach {item ->
            BottomNavigationItem(
                icon = { Icon(
                    imageVector = item.icon,
                    contentDescription = stringResource(id = item.label)
                ) },
                label = { Text(stringResource(id = item.label)) },
                selected = currentDestination?.hierarchy?.any { it.route == item.name } == true,
                selectedContentColor = Color.LightGray,
                unselectedContentColor = Color.DarkGray,
                onClick = {
                    navController.navigate(item.name) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }

}