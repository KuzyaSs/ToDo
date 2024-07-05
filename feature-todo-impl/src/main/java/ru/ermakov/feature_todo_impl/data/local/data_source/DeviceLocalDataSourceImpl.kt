package ru.ermakov.feature_todo_impl.data.local.data_source

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.provider.Settings.Secure.ANDROID_ID

class DeviceLocalDataSourceImpl(private val context: Context) : DeviceLocalDataSource {
    @SuppressLint("HardwareIds")
    override fun getDeviceId(): String {
        return Settings.Secure.getString(context.contentResolver, ANDROID_ID)
    }
}