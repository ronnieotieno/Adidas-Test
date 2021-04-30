package dev.ronnie.adidasandroid.api

import dev.ronnie.adidasandroid.data.entities.Product
import retrofit2.http.GET
import retrofit2.Retrofit

/**
 * A [Retrofit] service interface, see [Retrofit](https://square.github.io/retrofit/)
 */
interface ProductService {

    @GET("product")
    suspend fun getProducts(): List<Product>
}