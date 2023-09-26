package com.dviss.shoppinglist.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dviss.shoppinglist.ui.AppViewModel
import com.dviss.shoppinglist.ui.navigation.Route
import androidx.hilt.navigation.compose.hiltViewModel

private const val TAG = "AuthScreen"

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AuthScreen(
    viewmodel: AppViewModel = hiltViewModel(),
    navController: NavController,
    snackbarHostState: SnackbarHostState
) {
    val authState by viewmodel.authState.collectAsState()

    Log.d(TAG, "AuthScreen: i am recomposed!")

    var authTextFieldValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(
                authState.authKey,
                TextRange(authState.authKey.length)
            )
        )
    }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(authState.showSnackBar) {
        if (authState.showSnackBar) {
            snackbarHostState.showSnackbar(message = authState.snackBarMessage)
            viewmodel.dropSnackBarState()
        }
    }

    LaunchedEffect(authState.isAuthenticated) {
        if (authState.isAuthenticated) {
            Log.d(TAG, "AuthScreen: navigating to Lists Screen")
            navController.navigate(Route.LISTS)
        }
    }

    LaunchedEffect(authState.authKey) {
        authTextFieldValue = TextFieldValue(authState.authKey, TextRange(authState.authKey.length))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = authTextFieldValue,
            onValueChange = { authTextFieldValue = it },
            label = { Text(text = "Auth Key") },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewmodel.logIn(authTextFieldValue.text)
                keyboardController?.hide()
                focusRequester.freeFocus()
            },
            enabled = authTextFieldValue.text != "",
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Log In")
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                viewmodel.createAccount()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Create Account")
        }
    }
}