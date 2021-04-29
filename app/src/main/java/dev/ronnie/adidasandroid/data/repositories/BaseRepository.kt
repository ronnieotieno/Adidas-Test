package dev.ronnie.adidasandroid.data.repositories

import dev.ronnie.adidasandroid.utils.NetworkResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException


open class BaseRepository {

    /**
     * returns [NetworkResource.Success] of [T]  if the call is successful else returns a [NetworkResource.Failure]
     * Checks if the error originated from the backend and returns error body if present
     */
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): NetworkResource<T> {
        return withContext(Dispatchers.IO) {
            try {
                NetworkResource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        NetworkResource.Failure(
                            false,
                            throwable.code(),
                            throwable.response()?.errorBody()
                        )
                    }
                    else -> {
                        NetworkResource.Failure(true, null, null)
                    }
                }
            }
        }
    }
}