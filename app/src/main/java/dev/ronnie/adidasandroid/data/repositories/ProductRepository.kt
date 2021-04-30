package dev.ronnie.adidasandroid.data.repositories

import dev.ronnie.adidasandroid.api.ProductService
import dev.ronnie.adidasandroid.api.ReviewService
import dev.ronnie.adidasandroid.data.dao.ProductDao
import dev.ronnie.adidasandroid.data.entities.Product
import dev.ronnie.adidasandroid.data.entities.Review
import retrofit2.Retrofit

import javax.inject.Inject

/**
 * The app repository receiving the data from network, extends [BaseRepository] with generic class to handle exceptions while loading data
 * calls the [Retrofit] services function to load the data from the api.
 * Also calls [ProductDao] to access the local database
 */
class ProductRepository @Inject constructor(
    private val productService: ProductService,
    private val productDao: ProductDao,
    private val reviewService: ReviewService
) : BaseRepository() {

    fun getProducts() = productDao.getAllProducts()

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
