package com.dviss.shoppinglist.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewItemDialog(
    onShowAddNewItemDialogChange: (Boolean) -> Unit,
    onConfirmClick: (Int, String, Int?) -> Unit,
    listId: Int
) {
    var name by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(
                "",
                TextRange(0)
            )
        )
    }
    var amount by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(
                "1",
                TextRange(0)
            )
        )
    }
    var isErrorAmount by remember {
        mutableStateOf(false)
    }

    AlertDialog(
        onDismissRequest = { onShowAddNewItemDialogChange(false) },
        title = { Text(text = "Add item to shopping list") },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = "Item name") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = amount,
                    onValueChange = {
                        amount = it
                        isErrorAmount = amount.text.toIntOrNull() == null
                    },
                    label = { Text(text = "Amount") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = isErrorAmount,
                    supportingText = { Text(text = "Only whole numbers allowed")},
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmClick(listId, name.text, amount.text.toIntOrNull())
                    onShowAddNewItemDialogChange(false)
                },
                enabled = (name.text != "") && !isErrorAmount
            ) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = { onShowAddNewItemDialogChange(false) }) {
                Text(text = "Cancel")
            }
        }
    )
}