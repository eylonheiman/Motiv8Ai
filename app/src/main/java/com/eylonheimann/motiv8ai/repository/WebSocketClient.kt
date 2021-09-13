package com.eylonheimann.motiv8ai.repository


import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI


interface ItemsWebSocketClient {
    fun startStreamItems(): StateFlow<Item>
    fun stopStreamItems()
}

class WebSocketClientImpl : ItemsWebSocketClient {
    companion object {
        const val WEB_SOCKET_URL = "ws://superdo-groceries.herokuapp.com/receive"
        private const val TAG = "WebSocketClientImpl"
    }

    private lateinit var webSocketClient: WebSocketClient
    private var _newItem: MutableStateFlow<Item> = MutableStateFlow(Item("", "", ""))
    var newItem: StateFlow<Item> = _newItem
    var isClose = false

    init {
        initWebSocket()
    }

    override fun startStreamItems(): StateFlow<Item> {
        Log.e(TAG, "startStreamItems")
        if (!isClose) {
            webSocketClient.connect()
        } else {
            initWebSocket()
            webSocketClient.connect()
        }

        return newItem
    }

    override fun stopStreamItems() {
        webSocketClient.close()
        webSocketClient.connection.close()
    }

    private fun initWebSocket() {
        val coinbaseUri: URI? = URI(WEB_SOCKET_URL)
        createWebSocketClient(coinbaseUri)
    }

    private fun createWebSocketClient(coinbaseUri: URI?) {
        webSocketClient = object : WebSocketClient(coinbaseUri) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                Log.e(TAG, "onOpen")
            }

            override fun onMessage(message: String?) {
                Log.e(TAG, message ?: "")
                val gson = Gson()
                CoroutineScope(Dispatchers.IO).launch {
                    message?.let {
                        _newItem.emit(gson.fromJson(message, Item::class.java))
                    }
                }
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                Log.e(TAG, "onClose")
                isClose = true

            }

            override fun onError(ex: Exception?) {
                Log.e(TAG, "onError ${ex?.localizedMessage}")
            }
        }
    }
}
