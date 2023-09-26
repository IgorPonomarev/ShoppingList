package com.dviss.shoppinglist.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dviss.shoppinglist.ui.AppViewModel
import com.dviss.shoppinglist.ui.components.MyTopAppBar
import com.dviss.shoppinglist.ui.screen.AuthScreen
import com.dviss.shoppinglist.ui.screen.ListScreen
import com.dviss.shoppinglist.ui.screen.ListsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val viewmodel: AppViewModel = viewModel()

    //state of current navigation route
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Route.AUTH
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            MyTopAppBar(viewModel = viewmodel, navController = navController, currentRoute)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Route.AUTH,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Route.AUTH) {
                AuthScreen(
                    navController = navController,
                    viewmodel = viewmodel,
                    snackbarHostState = snackbarHostState
                )
            }
            composable(Route.LISTS) {
                ListsScreen(navController = navController, viewmodel = viewmodel)
            }
            composable(
                Route.LIST_ITEMS,
                //arguments = listOf(navArgument("listId") { type = NavType.IntType })
            ) { backStackEntry ->
                ListScreen(
                    navController = navController,
                    viewmodel = viewmodel,
                    //listId = backStackEntry.arguments?.getInt("listId")
                )
            }
        }
    }
}