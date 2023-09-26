package com.dviss.shoppinglist.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewListDialog(
    onShowCreateNewListDialogChange: (Boolean) -> Unit,
    onConfirmClick: (String) -> Unit
) {
    var name by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(
                "",
                TextRange(0)
            )
        )
    }

    AlertDialog(
        onDismissRequest = { onShowCreateNewListDialogChange(false) },
        title = { Text(text = "Create new shopping list") },
        text = {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "List name") },
                singleLine = true
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmClick(name.text)
                    onShowCreateNewListDialogChange(false)
                },
                enabled = name.text != ""
            ) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = { onShowCreateNewListDialogChange(false) }) {
                Text(text = "Cancel")
            }
        }
    )
}