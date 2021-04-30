package dev.ronnie.adidasandroid.data.repositories

import android.net.Network
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.ronnie.adidasandroid.api.ProductService
import dev.ronnie.adidasandroid.api.ReviewService
import dev.ronnie.adidasandroid.data.dao.ProductDao
import dev.ronnie.adidasandroid.data.entities.Product
import dev.ronnie.adidasandroid.data.entities.Review
import dev.ronnie.adidasandroid.utils.Event
import dev.ronnie.adidasandroid.utils.NetworkResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productService: ProductService,
    private val productDao: ProductDao,
    private val reviewService: ReviewService
) : BaseRepository() {

    suspend fun getProducts() = productDao.getAllProducts()

    suspend fun fetchProducts() = safeApiCall {
        productService.getProducts()
    }

    suspend fun saveData(list: List<Product>) = productDao.insertMultipleProducts(list)
    fun getSingleProduct(id: String) = productDao.getSingleProduct(id)

    suspend fun postReview(review: Review) = safeApiCall {
        reviewService.postReview(review.productId, review)
    }

    suspend fun insertProduct(product: Product) = productDao.insertProduct(product)

}
