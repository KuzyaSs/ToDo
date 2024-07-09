package ru.ermakov.core.network_manager

import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class NetworkManagerImpl(private val connectivityManager: ConnectivityManager) : NetworkManager {
    override fun getNetworkStatus(): Flow<NetworkStatus> {
        return callbackFlow {
            launch { send(NetworkStatus.LOST) }
            val networkCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(NetworkStatus.AVAILABLE) }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(NetworkStatus.LOST) }
                }
            }
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
            awaitClose {
                connectivityManager.unregisterNetworkCallback(networkCallback)
            }
        }
    }
}