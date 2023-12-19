package com.strawhead.ecolution.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("Home")
    object Bookmark : Screen("Bookmark")
    object Add : Screen("Add")
    object Profile : Screen("Profile")

}