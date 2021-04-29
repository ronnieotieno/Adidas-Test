package dev.ronnie.adidasandroid.utils

import okhttp3.ResponseBody

/**
 * A Kotlin sealed class that exposes the different classes regarding the state of network call
 */
sealed class NetworkResource<out T> {
    data class Success<out T>(val value: T) : NetworkResource<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : NetworkResource<Nothing>()

    object Loading : NetworkResource<Nothing>()
}

