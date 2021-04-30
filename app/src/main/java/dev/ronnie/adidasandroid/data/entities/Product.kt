package dev.ronnie.adidasandroid.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import dev.ronnie.adidasandroid.utils.ReviewConverter
import kotlinx.parcelize.Parcelize

/**
 * Product Object which also act as [Entity] class with table name
 */
@Entity(tableName = "products_table")

//converting the list to string inorder to be stored to db and vice versa inorder to be shown to the user
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