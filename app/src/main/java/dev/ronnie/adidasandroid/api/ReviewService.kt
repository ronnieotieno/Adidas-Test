package dev.ronnie.adidasandroid.api

import dev.ronnie.adidasandroid.data.entities.Product
import dev.ronnie.adidasandroid.data.entities.Review
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * A [Retrofit] service interface, see [Retrofit](https://square.github.io/retrofit/)
 */
interface ReviewService {
    @POST("reviews/{id}")
    suspend fun postReview(
        @Path("id") productId: String,
        @Body review: Review
    ): Review
}