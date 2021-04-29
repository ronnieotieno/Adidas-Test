package dev.ronnie.adidasandroid.presentation.viewModels

import androidx.lifecycle.ViewModel
import dev.ronnie.adidasandroid.data.entities.Product
import dev.ronnie.adidasandroid.data.repositories.ProductRepository
import javax.inject.Inject

class ProductListViewModel @Inject constructor(private val repository: ProductRepository) :
    ViewModel() {

    val networkStatus = repository.networkStatus

    var productList = ArrayList<Product>()

    suspend fun getProducts() = repository.getProducts()

}