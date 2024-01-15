package com.rizkifauzi.excitingnews.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("home/{newsId}"){
        fun createRoute(newsId: Long) = "home/$newsId"
    }
    object Search : Screen("search")
    object Bookmark : Screen("bookmark")
    object About : Screen("about")
}