package com.rizkifauzi.excitingnews

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument

import com.rizkifauzi.excitingnews.ui.navigation.NavigationItem
import com.rizkifauzi.excitingnews.ui.navigation.Screen
import com.rizkifauzi.excitingnews.ui.screen.about.AboutScreen
import com.rizkifauzi.excitingnews.ui.screen.bookmark.BookmarkScreen
import com.rizkifauzi.excitingnews.ui.screen.detail.DetailScreen
import com.rizkifauzi.excitingnews.ui.screen.home.HomeScreen
import com.rizkifauzi.excitingnews.ui.screen.search.SearchScreen
import com.rizkifauzi.excitingnews.ui.theme.ExcitingNewsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExcitingNewsApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold (
        bottomBar = {
            if (currentRoute != Screen.Detail.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ){
        innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(innerPadding)
            ){
                composable(Screen.Home.route) {
                    HomeScreen(
                        navigateToDetail = {newsId ->
                            navController.navigate(Screen.Detail.createRoute(newsId))
                        },
                        navController = navController
                    )
                }
                composable(Screen.Search.route) {
                    SearchScreen(
                        navigateToDetail = {newsId ->
                            navController.navigate(Screen.Detail.createRoute(newsId))
                        },
                        navController = navController
                    )
                }
                composable(Screen.Bookmark.route) {
                    BookmarkScreen(
                        navigateToDetail = {newsId ->
                            navController.navigate(Screen.Detail.createRoute(newsId))
                        },
                        navController = navController
                    )
                }
                composable(Screen.About.route) {
                    AboutScreen()
                }
                composable(
                    route = Screen.Detail.route,
                    arguments = listOf(navArgument("newsId") { type = NavType.LongType})
                ){
                    val id = it.arguments?.getLong("newsId") ?: -1L
                    DetailScreen(
                        newsId = id,
                        navigateBack = {
                            navController.navigateUp()
                        }
                    )
                }
            }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier){
    NavigationBar (
        modifier = modifier
            .height(60.dp)
    ){
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.home_title),
                icon = Icons.Outlined.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.search_title),
                icon = Icons.Default.Search,
                screen = Screen.Search
            ),
            NavigationItem(
                title = stringResource(R.string.bookmark_title),
                icon = Icons.Default.FavoriteBorder,
                screen = Screen.Bookmark
            ),
            NavigationItem(
                title = stringResource(R.string.home_title),
                icon = Icons.Outlined.Info,
                screen = Screen.About
            )
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route){
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExcitingNewsAppPreview(){
    ExcitingNewsTheme{
        ExcitingNewsApp()
    }
}