package dev.ronnie.adidasandroid.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import dev.ronnie.adidasandroid.utils.ReviewConverter
import kotlinx.parcelize.Parcelize

@Entity(tableName = "products_table")
@TypeConverters(
    ReviewConverter::class
)
@Parcelize
data class Product(
    val currency: String,
    var description: String,
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val imgUrl: String,
    var name: String,
    val price: Int,
    var reviews: List<Review>
) : Parcelable