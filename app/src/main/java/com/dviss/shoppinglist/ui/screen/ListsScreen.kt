package com.dviss.shoppinglist.ui.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dviss.shoppinglist.ui.AppViewModel
import com.dviss.shoppinglist.ui.components.CreateNewListDialog
import com.dviss.shoppinglist.ui.navigation.Route

private const val TAG = "ListsScreen"

@Composable
fun ListsScreen(
    viewmodel: AppViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewmodel.listsState.collectAsState()

    Log.d(TAG, "ListsScreen: i am recomposed!")

    var showCreateNewListDialog by remember { mutableStateOf(false) }
    if (showCreateNewListDialog) {
        CreateNewListDialog(
            onShowCreateNewListDialogChange = { showCreateNewListDialog = it },
            onConfirmClick = viewmodel::createShoppingList
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(state.shoppingLists) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clickable {
                            viewmodel.updateCurrentList(it.id)
                            //navController.navigate(Route.LIST_ITEMS + "/${it.id}")
                            navController.navigate(Route.LIST_ITEMS)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = it.name)
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { viewmodel.removeShoppingList(it.id) }) {
                        Icon(Icons.Filled.Delete, "delete")
                    }
                }
                Divider()
            }
        }
        FloatingActionButton(
            onClick = { showCreateNewListDialog = true },
            modifier = Modifier.align(alignment = Alignment.BottomEnd)
        ) {
            Icon(Icons.Filled.Add, "Add list")
        }
    }
}

