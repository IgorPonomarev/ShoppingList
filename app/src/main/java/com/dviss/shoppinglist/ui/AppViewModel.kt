package com.dviss.shoppinglist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dviss.shoppinglist.domain.model.NetworkResponse
import com.dviss.shoppinglist.domain.usecase.AppUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val useCases: AppUseCases
) : ViewModel() {

    private val _listsState = MutableStateFlow(ListsState())
    val listsState = _listsState.asStateFlow()
    private val _listState = MutableStateFlow(ListState())
    val listState = _listState.asStateFlow()
    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()

    init {
        viewModelScope.launch {
            useCases.getAuthKey().collect { newKey ->
                _authState.update { it.copy(authKey = newKey) }
            }
        }
        viewModelScope.launch {
            _authState.collectLatest {
                useCases.getAllShopLists(_authState.value.authKey)
                    .collect { newLists ->
                        _listsState.update {
                            it.copy(
                                shoppingLists = newLists
                            )
                        }
                    }
            }
        }
        viewModelScope.launch {
            _listsState.collectLatest {
                useCases.getShoppingListItems(_listsState.value.currentListId)
                    .collect { newListItems ->
                        _listState.update { currentListState ->
                            currentListState.copy(
                                currentListItems = newListItems
                            )
                        }
                    }
            }
        }
        viewModelScope.launch {
            while (true) {
                if (authState.value.authKey == "") {
                    delay(1000)
                } else {
                    useCases.updateShoppingLists(authState.value.authKey)
                    delay(1000)
                }
            }
        }
        viewModelScope.launch {
            while (true) {
                if (listsState.value.currentListId == 0) {
                    delay(1000)
                } else {
                    useCases.updateShoppingList(listsState.value.currentListId)
                    delay(1000)
                }
            }
        }
    }

    fun logIn(authKey: String = authState.value.authKey) {
        viewModelScope.launch {
            when (val response = useCases.logIn(authKey)) {
                is NetworkResponse.Success ->
                    _authState.update {
                        it.copy(isAuthenticated = true)
                    }

                is NetworkResponse.Error ->
                    _authState.update {
                        it.copy(showSnackBar = true, snackBarMessage = response.message)
                    }

                else -> {}
            }
        }
    }

    fun createAccount() {
        viewModelScope.launch {
            val newKey = useCases.createAccount()
            if (newKey != "") {
                _authState.update {
                    it.copy(authKey = newKey, isAuthenticated = true)
                }
            } else {
                _authState.update {
                    it.copy(showSnackBar = true, snackBarMessage = "network error")
                }
            }
        }
    }

    fun createShoppingList(name: String) {
        viewModelScope.launch {
            useCases.createShoppingList(authState.value.authKey, name)
            useCases.updateShoppingLists(authState.value.authKey)
        }
    }

    fun removeShoppingList(listId: Int) {
        viewModelScope.launch {
            useCases.removeShoppingList(listId)
            useCases.updateShoppingLists(authState.value.authKey)
        }
    }

    fun removeItemFromList(listId: Int, itemId: Int) {
        viewModelScope.launch {
            useCases.removeFromList(listId, itemId)
            useCases.updateShoppingList(listsState.value.currentListId)
        }
    }

    fun updateCurrentList(listId: Int) {
        _listsState.update {
            it.copy(currentListId = listId)
        }
    }

    fun crossItOff(listId: Int, itemId: Int) {
        viewModelScope.launch {
            useCases.crossItOff(listId, itemId)
            useCases.updateShoppingList(listsState.value.currentListId)
        }
    }

    fun addItemToList(listId: Int, name: String, amount: Int?) {
        if (amount != null) {
            viewModelScope.launch {
                useCases.addToShoppingList(listId, name, amount)
                useCases.updateShoppingList(listsState.value.currentListId)
            }
        }
    }

    fun logOut() {
        _authState.update {
            it.copy(isAuthenticated = false)
        }
    }

    fun dropSnackBarState() {
        _authState.update {
            it.copy(showSnackBar = false, snackBarMessage = "")
        }
    }

}