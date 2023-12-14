package com.strawhead.ecolution.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("Home")
    object Favorite : Screen("Favorite")
    object Add : Screen("Add")
    object Profile : Screen("Profile")

}