package com.eylonheimann.motiv8ai.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eylonheimann.motiv8ai.repository.Item
import com.eylonheimann.motiv8ai.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    companion object {
        const val TAG = "HomeViewModel"
    }

    var items: StateFlow<Item> = repository.newItem
    var isStart = false

    init {
        viewModelScope.launch {
            repository.startStreamItems()
            isStart = true
        }
    }

    fun startStreamItems() {
        viewModelScope.launch {
            repository.startStreamItems()
            isStart = true
        }
    }

    fun stopSteamItems() {
        viewModelScope.launch {
            Log.d(TAG, "stopSteamItems")
            repository.stopStreamItems()
        }
    }

    fun getSavedItems(): List<Item> {
        return repository.getAllSavedItems()
    }

    override fun onCleared() {
        super.onCleared()
        CoroutineScope(Dispatchers.IO).launch {
            repository.stopStreamItems()
        }
    }
}