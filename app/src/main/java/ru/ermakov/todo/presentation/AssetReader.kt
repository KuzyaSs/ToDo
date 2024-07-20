package ru.ermakov.todo.presentation

import android.content.Context
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

private const val BUFFER_SIZE = 2048

class AssetReader(private val context: Context) {

    fun read(filename: String): JSONObject {
        val data = toString(context.assets.open(filename))
        return JSONObject(data)
    }
}

private fun toString(inputStream: InputStream): String {
    val buffer = CharArray(BUFFER_SIZE)
    val reader = BufferedReader(InputStreamReader(inputStream))
    val builder = StringBuilder(inputStream.available())
    var read: Int
    while (reader.read(buffer).also { read = it } != -1) {
        builder.append(buffer, 0, read)
    }
    return builder.toString()
}