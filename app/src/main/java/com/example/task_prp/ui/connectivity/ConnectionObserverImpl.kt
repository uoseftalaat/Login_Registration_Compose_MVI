package com.example.task_prp.ui.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class ConnectionObserverImpl(
    context: Context
):ConnectivityObserver {

    private val connectionManager:ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    override fun connectionObserver(): Flow<ConnectivityObserver.ConnectionStatus> {
        return callbackFlow {
            val callback = object: ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(ConnectivityObserver.ConnectionStatus.Connected) }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(ConnectivityObserver.ConnectionStatus.Offline) }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(ConnectivityObserver.ConnectionStatus.Offline) }
                }
            }

            connectionManager.registerDefaultNetworkCallback(callback)
            awaitClose{
                connectionManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }
}