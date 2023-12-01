package com.strawhead.ecolution.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("Home")
    object Favorite : Screen("Favorite")
    object Maps : Screen("Maps")
    object Profile : Screen("Profile")

}