package com.dviss.shoppinglist.data.remote

import android.util.Log
import com.dviss.shoppinglist.data.remote.dto.AddToShoppingListResponse
import com.dviss.shoppinglist.data.remote.dto.AuthResponse
import com.dviss.shoppinglist.data.remote.dto.CreateShoppingListResponse
import com.dviss.shoppinglist.data.remote.dto.CrossItOffResponse
import com.dviss.shoppinglist.data.remote.dto.GetAllMyShopListsResponse
import com.dviss.shoppinglist.data.remote.dto.GetShoppingListResponse
import com.dviss.shoppinglist.data.remote.dto.RemoveFromListResponse
import com.dviss.shoppinglist.data.remote.dto.RemoveShoppingListResponse
import com.dviss.shoppinglist.data.remote.dto.ShoppingListDTO
import com.dviss.shoppinglist.data.remote.dto.ShoppingListItemDTO
import com.dviss.shoppinglist.data.remote.dto.toShoppingList
import com.dviss.shoppinglist.data.remote.dto.toShoppingListItem
import com.dviss.shoppinglist.domain.model.NetworkResponse
import com.dviss.shoppinglist.domain.model.ShoppingList
import com.dviss.shoppinglist.domain.model.ShoppingListItem
import com.dviss.shoppinglist.domain.service.ShoppingListService
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.post

private const val TAG = "ShoppingListServiceImpl"

class ShoppingListServiceImpl(
    private val client: HttpClient
) : ShoppingListService {

    //generate test auth key
    override suspend fun createTestKey(): String {
        Log.d(TAG, "createTestKey: trying to create new key")
        val result = try {
            client.post(HttpRoutes.MAIN_URL + ApiActions.CreateTestKey.NAME).body()
        } catch (e: Exception) {
            parseException(e, ApiActions.CreateTestKey.NAME)
            ""
        }
        Log.d(TAG, "createTestKey: key $result generated")
        return result
    }

    //auth via generated key
    override suspend fun authentication(authKey: String): NetworkResponse {
        return try {
            val response = client.post(HttpRoutes.MAIN_URL + ApiActions.Authentication.NAME) {
                url {
                    parameters.append(ApiActions.Authentication.Parameters.KEY, authKey)
                }
            }.body<AuthResponse>()

            Log.d(TAG, "authentication: success: ${response.success}")

            if (response.success) {
                NetworkResponse.Success
            } else {
                NetworkResponse.Error("Wrong Key")
            }
        } catch (e: Exception) {
            parseException(e, ApiActions.Authentication.NAME)
            NetworkResponse.Error("Check your internet connection")
        }
    }

    override suspend fun createShoppingList(authKey: String, listName: String): Pair<Int, Boolean> {
        val response = try {
            client.post(HttpRoutes.MAIN_URL + ApiActions.CreateShoppingList.NAME) {
                url {
                    parameters.append(ApiActions.CreateShoppingList.Parameters.KEY, authKey)
                    parameters.append(ApiActions.CreateShoppingList.Parameters.NAME, listName)
                }
            }.body()
        } catch (e: Exception) {
            parseException(e, ApiActions.CreateShoppingList.NAME)
            CreateShoppingListResponse(false, 0)
        }
        if (response.success)
            Log.d(TAG, "createShoppingList: new list with id ${response.listId} created")
        Log.d(TAG, "createShoppingList: success: ${response.success}")
        return Pair(response.listId, response.success)
    }

    override suspend fun removeShoppingList(listId: Int): Boolean {
        val response = try {
            client.post(HttpRoutes.MAIN_URL + ApiActions.RemoveShoppingList.NAME) {
                url {
                    parameters.append(
                        ApiActions.RemoveShoppingList.Parameters.LIST_ID,
                        listId.toString()
                    )
                }
            }.body()
        } catch (e: Exception) {
            parseException(e, ApiActions.RemoveShoppingList.NAME)
            RemoveShoppingListResponse(false, false)
        }
        Log.d(TAG, "removeShoppingList: success: ${response.success}")
        return response.success
    }

    override suspend fun addToShoppingList(
        listId: Int,
        itemName: String,
        amount: Int
    ): Pair<Int, Boolean> {
        val response = try {
            client.post(HttpRoutes.MAIN_URL + ApiActions.AddToShoppingList.NAME) {
                url {
                    parameters.append(
                        ApiActions.AddToShoppingList.Parameters.LIST_ID,
                        listId.toString()
                    )
                    parameters.append(ApiActions.AddToShoppingList.Parameters.NAME, itemName)
                    parameters.append(
                        ApiActions.AddToShoppingList.Parameters.AMOUNT,
                        amount.toString()
                    )
                }
            }.body()
        } catch (e: Exception) {
            parseException(e, ApiActions.AddToShoppingList.NAME)
            AddToShoppingListResponse(false, 0)
        }
        Log.d(TAG, "addToShoppingList: success: ${response.success}")
        return Pair(response.itemId, response.success)
    }

    override suspend fun crossItOff(listId: Int, itemId: Int): Boolean {
        val response = try {
            client.post(HttpRoutes.MAIN_URL + ApiActions.CrossItOff.NAME) {
                url {
                    parameters.append(ApiActions.CrossItOff.Parameters.LIST_ID, listId.toString())
                    parameters.append(ApiActions.CrossItOff.Parameters.ITEM_ID, itemId.toString())
                }
            }.body()
        } catch (e: Exception) {
            parseException(e, ApiActions.CrossItOff.NAME)
            CrossItOffResponse(false, 0)
        }
        Log.d(TAG, "crossItOff: success: ${response.success}")
        return response.success
    }

    override suspend fun removeFromList(listId: Int, itemId: Int): Boolean {
        val response = try {
            client.post(HttpRoutes.MAIN_URL + ApiActions.RemoveFromList.NAME) {
                url {
                    parameters.append(
                        ApiActions.RemoveFromList.Parameters.LIST_ID,
                        listId.toString()
                    )
                    parameters.append(
                        ApiActions.RemoveFromList.Parameters.ITEM_ID,
                        itemId.toString()
                    )
                }
            }.body()
        } catch (e: Exception) {
            parseException(e, ApiActions.RemoveFromList.NAME)
            RemoveFromListResponse(false)
        }
        Log.d(TAG, "removeFromList: success: ${response.success}")
        return response.success
    }

    override suspend fun getAllMyShopLists(authKey: String): List<ShoppingList> {
        val response = try {
            client.post(HttpRoutes.MAIN_URL + ApiActions.GetAllMyShopLists.NAME) {
                url {
                    parameters.append(ApiActions.GetAllMyShopLists.Parameters.KEY, authKey)
                }
            }.body()
        } catch (e: Exception) {
            parseException(e, ApiActions.GetAllMyShopLists.NAME)
            GetAllMyShopListsResponse(false, emptyList<ShoppingListDTO>())
        }
        Log.d(TAG, "getAllMyShopLists: success: ${response.success}")
        return response.shoppingLists.map { it.toShoppingList() }
    }

    override suspend fun getShoppingList(listId: Int): List<ShoppingListItem> {
        val response = try {
            client.post(HttpRoutes.MAIN_URL + ApiActions.GetShoppingList.NAME) {
                url {
                    parameters.append(
                        ApiActions.GetShoppingList.Parameters.LIST_ID,
                        listId.toString()
                    )
                }
            }.body()
        } catch (e: Exception) {
            parseException(e, ApiActions.GetShoppingList.NAME)
            GetShoppingListResponse(false, emptyList<ShoppingListItemDTO>())
        }
        Log.d(TAG, "getShoppingList: success: ${response.success}")
        return response.itemsLists.map { it.toShoppingListItem() }
    }

    private fun parseException(e: Exception, funName: String) {
        when (e) {
            is RedirectResponseException -> Log.d(TAG, "$funName: ${e.response.status.description}")
            is ClientRequestException -> Log.d(TAG, "$funName: ${e.response.status.description}")
            is ServerResponseException -> Log.d(TAG, "$funName: ${e.response.status.description}")
            is NoTransformationFoundException -> Log.d(TAG, "$funName: ${e.message}")
            else -> Log.d(TAG, "$funName: ${e.message}")
        }
    }
}