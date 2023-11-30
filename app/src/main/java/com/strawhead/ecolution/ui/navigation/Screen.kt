package com.strawhead.ecolution.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Maps : Screen("maps")
    object Profile : Screen("profile")

}