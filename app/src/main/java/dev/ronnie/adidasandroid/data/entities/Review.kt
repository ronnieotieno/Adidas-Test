package dev.ronnie.adidasandroid.data.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Review(
    val locale: String,
    val productId: String,
    val rating: Int,
    var text: String,
) : Parcelable