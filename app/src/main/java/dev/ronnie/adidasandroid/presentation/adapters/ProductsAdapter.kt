package dev.ronnie.adidasandroid.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.ronnie.adidasandroid.data.entities.Product
import dev.ronnie.adidasandroid.databinding.ProductItemBinding
import dev.ronnie.adidasandroid.utils.ProductDiffCallback

class ProductsAdapter(val onClick: (Product) -> Unit) :
    RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    private val productList: ArrayList<Product> = ArrayList()


    fun filter(list: List<Product>, query: String) {

        if (query.trim().isNotEmpty()) {
            list.filter {
                it.description.contains(query, ignoreCase = true) || it.name.contains(
                    query,
                    ignoreCase = true
                )
            }.apply {
                setData(this)
            }
        } else {
            setData(list)
        }

    }


    fun setData(list: List<Product>) {
        val diffCallback = ProductDiffCallback(productList, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        productList.clear()
        productList.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {

        return ProductsViewHolder(
            ProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = productList[position]
        holder.setData(product)
    }

    override fun getItemCount() = productList.size

    inner class ProductsViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(product: Product) {
            binding.root.setOnClickListener {
                onClick.invoke(product)
            }
            product.apply {
                val desc = description.capitalize()
                val name = name.capitalize()
                this.name = name
                this.description = desc
                binding.product = this
            }

        }

    }
}