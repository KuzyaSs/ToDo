package ru.ermakov.core.network_manager

import kotlinx.coroutines.flow.Flow

interface NetworkManager {
    fun getNetworkStatus(): Flow<NetworkStatus>
}