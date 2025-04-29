package com.ramphal.todoapp.navigation

sealed class Screen(val route: String) {
    object homeView: Screen(route = "HomeView")
    object detailView: Screen(route = "DetailView")
}