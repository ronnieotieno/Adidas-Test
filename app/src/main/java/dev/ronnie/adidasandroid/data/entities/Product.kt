package dev.ronnie.adidasandroid.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import dev.ronnie.adidasandroid.utils.ReviewConverter

@Entity(tableName = "products_table")
@TypeConverters(
    ReviewConverter::class
)
data class Product(
    val currency: String,
    var description: String,
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val imgUrl: String,
    var name: String,
    val price: Int,
    val reviews: List<Review>
)