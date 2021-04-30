package dev.ronnie.adidasandroid.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.ronnie.adidasandroid.data.entities.Product
import dev.ronnie.adidasandroid.data.entities.Review
import dev.ronnie.adidasandroid.databinding.ProductItemBinding
import dev.ronnie.adidasandroid.databinding.RatingItemBinding
import dev.ronnie.adidasandroid.presentation.adapters.ProductsAdapter.ProductsViewHolder
import dev.ronnie.adidasandroid.utils.ProductDiffCallback
import dev.ronnie.adidasandroid.utils.RatingDiffCallback

/**
 * A simple [RecyclerView.Adapter] that loads the list of [Review] to the Recyclerview
 */
class RatingsAdapter : RecyclerView.Adapter<RatingsAdapter.RatingViewHolder>() {

    private val ratingList: ArrayList<Review> = ArrayList()


    fun setData(list: List<Review>) {
        val diffCallback = RatingDiffCallback(ratingList, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        ratingList.clear()
        ratingList.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingViewHolder {

        return RatingViewHolder(
            RatingItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: RatingViewHolder, position: Int) {
        val review = ratingList[position]
        holder.setData(review)
    }

    override fun getItemCount() = ratingList.size

    inner class RatingViewHolder(private val binding: RatingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(review: Review) {

            val text = review.text.capitalize()
            review.text = text
            binding.review = review

        }

    }
}