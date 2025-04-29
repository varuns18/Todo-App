package com.ramphal.todoapp.navigation

import android.icu.text.TimeZoneNames
import android.icu.text.TimeZoneNames.NameType
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ramphal.todoapp.DetailView
import com.ramphal.todoapp.HomeView
import com.ramphal.todoapp.viewmodel.TodoViewModel
import java.util.Map.entry

@Composable
fun Navigation(
    viewModel: TodoViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
){
    NavHost(
        navController = navController,
        startDestination = Screen.homeView.route
    ){
        composable(route = Screen.homeView.route) {
            HomeView(navController = navController, viewModel = viewModel)
        }
        composable(route = Screen.detailView.route + "/{id}",
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false
                }
            )
        ) {backStackEntry ->
            val id = if(backStackEntry.arguments != null) backStackEntry.arguments!!.getLong("id") else 0L
            DetailView(id = id, viewModel = viewModel, navController = navController)
        }
    }
}