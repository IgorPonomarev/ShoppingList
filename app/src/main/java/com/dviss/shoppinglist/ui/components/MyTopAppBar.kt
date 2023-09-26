package com.dviss.shoppinglist.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.dviss.shoppinglist.R
import com.dviss.shoppinglist.ui.AppViewModel
import com.dviss.shoppinglist.ui.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    viewModel: AppViewModel,
    navController: NavController,
    currentRoute: String
) {
    val state by viewModel.listsState.collectAsState()
    val authState by viewModel.authState.collectAsState()

    //title text
    val title =
        if (currentRoute == Route.AUTH) {
            "Shopping List"
        } else if (currentRoute == Route.LISTS) {
            "USER: ${authState.authKey}"
        } else {
            state.shoppingLists.find { it.id == state.currentListId }?.name ?: ""
        }

    TopAppBar(
        title = { Text(text = title) },
        actions = {
            if (currentRoute == Route.LISTS) {
                IconButton(onClick = {
                    viewModel.logOut()
                    navController.navigate(Route.AUTH)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_logout_24),
                        contentDescription = "log out"
                    )
                }
            }
        },
        navigationIcon = {
            if (currentRoute == Route.LIST_ITEMS) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Default.ArrowBack, "back")
                }
            }
        }
    )
}