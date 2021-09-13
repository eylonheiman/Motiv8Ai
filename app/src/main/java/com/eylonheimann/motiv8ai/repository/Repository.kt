package com.eylonheimann.motiv8ai.repository

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

interface Repository {
    suspend fun startStreamItems()
    suspend fun stopStreamItems()
    fun getAllSavedItems(): List<Item>
    var newItem: StateFlow<Item>
}

class RepositoryImpl @Inject constructor(private val webSocketClient: ItemsWebSocketClient) :
    Repository {
    companion object {
        const val TAG = "RepositoryImpl"
    }

    private var savedItems = mutableListOf<Item>() // TODO DB
    
    private var _newItem: MutableStateFlow<Item> = MutableStateFlow(Item("", "", ""))
    override var newItem: StateFlow<Item> = _newItem

    override suspend fun startStreamItems() {
        webSocketClient.startStreamItems().collect {
            savedItems.add(it)
            Log.e(TAG, "collect and emit $it")
            _newItem.emit(it)
        }
    }

    override suspend fun stopStreamItems() {
        Log.d(TAG, "stopStreamItems")
        webSocketClient.stopStreamItems()
    }

    override fun getAllSavedItems(): List<Item> {
        return savedItems
    }
}