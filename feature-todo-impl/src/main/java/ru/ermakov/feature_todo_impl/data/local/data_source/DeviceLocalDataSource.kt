package ru.ermakov.feature_todo_impl.data.local.data_source
/**
 * Manages the device id.
 */
interface DeviceLocalDataSource {
    fun getDeviceId(): String
}