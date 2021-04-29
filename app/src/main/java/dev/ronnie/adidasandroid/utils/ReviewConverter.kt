package dev.ronnie.adidasandroid.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.ronnie.adidasandroid.data.entities.Review
import java.lang.reflect.Type

class ReviewConverter {
    private val gSon = Gson()

    @TypeConverter
    fun from(list: List<Review>?): String? {
        if (list == null) {
            return null
        }

        val type: Type = object : TypeToken<List<Review?>>() {}.type
        return gSon.toJson(list, type)
    }

    @TypeConverter
    fun to(listString: String?): List<Review>? {
        if (listString == null) {
            return null
        }
        val type: Type = object : TypeToken<List<Review>?>() {}.type
        return gSon.fromJson(listString, type)
    }
}