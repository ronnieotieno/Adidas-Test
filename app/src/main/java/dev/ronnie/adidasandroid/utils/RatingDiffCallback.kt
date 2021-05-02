package dev.ronnie.adidasandroid.utils

import androidx.recyclerview.widget.DiffUtil
import dev.ronnie.adidasandroid.data.entities.Product
import dev.ronnie.adidasandroid.data.entities.Review

/**
 * Compares items to avoid dropping the whole list when the Adapter list changes.
 */
class RatingDiffCallback(private val oldList: List<Review>, private val newList: List<Review>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].text == newList[newItemPosition].text
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val (_, value, id1) = oldList[oldPosition]
        val (_, value1, id2) = newList[newPosition]

        return id1 == id2 && value == value1
    }

    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}