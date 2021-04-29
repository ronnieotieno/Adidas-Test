package dev.ronnie.adidasandroid.api

import dev.ronnie.adidasandroid.data.entities.Product
import retrofit2.http.GET

interface ProductService {

    @GET("product")
    suspend fun getProducts(): List<Product>
}