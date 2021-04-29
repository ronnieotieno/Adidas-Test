package dev.ronnie.adidasandroid.data.entities

data class Review(
    val locale: String,
    val productId: String,
    val rating: Int,
    val text: String
)