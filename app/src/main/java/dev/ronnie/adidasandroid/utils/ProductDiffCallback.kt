package dev.ronnie.adidasandroid.utils

import androidx.recyclerview.widget.DiffUtil
import dev.ronnie.adidasandroid.data.entities.Product

class ProductDiffCallback(private val oldList: List<Product>, private val newList: List<Product>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id === newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val (_, value, id) = oldList[oldPosition]
        val (_, value1, id1) = newList[newPosition]

        return id == id1 && value == value1
    }

    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}