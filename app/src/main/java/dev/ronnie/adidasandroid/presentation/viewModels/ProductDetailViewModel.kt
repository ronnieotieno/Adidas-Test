package dev.ronnie.adidasandroid.presentation.viewModels

import androidx.lifecycle.ViewModel
import dev.ronnie.adidasandroid.data.repositories.ProductRepository
import javax.inject.Inject

class ProductDetailViewModel @Inject constructor(private val repository: ProductRepository) :
    ViewModel() {
}