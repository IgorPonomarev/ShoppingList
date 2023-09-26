package com.dviss.shoppinglist.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dviss.shoppinglist.ui.AppViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.dviss.shoppinglist.ui.components.AddNewItemDialog

private const val TAG = "ListsScreen"

@Composable
fun ListScreen(
    viewmodel: AppViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewmodel.listsState.collectAsState()
    val listState by viewmodel.listState.collectAsState()

    Log.d(TAG, "ListScreen: i am recomposed!")

    var showAddNewItemDialog by remember { mutableStateOf(false) }
    if (showAddNewItemDialog) {
        AddNewItemDialog(
            onShowAddNewItemDialogChange = { showAddNewItemDialog = it },
            onConfirmClick = viewmodel::addItemToList,
            listId = state.currentListId
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
            if (listState.currentListItems.isEmpty()) {
                item { Text(text = "No items here yet") }
            }
            items(listState.currentListItems) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = item.isCrossed,
                        onCheckedChange = {
                            viewmodel.crossItOff(
                                state.currentListId,
                                item.itemId
                            )
                        })
                    Text(
                        text = item.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = item.amount.toString())
                    Spacer(modifier = Modifier.width(16.dp))
                    IconButton(
                        onClick = {
                            viewmodel.removeItemFromList(
                                state.currentListId,
                                item.itemId
                            )
                        },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(Icons.Filled.Delete, "delete")
                    }
                }
                Divider()
            }
        }
        FloatingActionButton(
            onClick = { showAddNewItemDialog = true },
            modifier = Modifier.align(alignment = Alignment.BottomEnd)
        ) {
            Icon(Icons.Filled.Add, "Add item")
        }
    }
}

