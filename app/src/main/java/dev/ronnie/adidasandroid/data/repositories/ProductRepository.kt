package dev.ronnie.adidasandroid.data.repositories

import android.net.Network
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.ronnie.adidasandroid.api.ProductService
import dev.ronnie.adidasandroid.data.dao.ProductDao
import dev.ronnie.adidasandroid.data.entities.Product
import dev.ronnie.adidasandroid.utils.Event
import dev.ronnie.adidasandroid.utils.NetworkResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productService: ProductService,
    private val productDao: ProductDao
) : BaseRepository() {

    private val _status: MutableLiveData<Event<Boolean>> =
        MutableLiveData()
    val networkStatus: LiveData<Event<Boolean>> get() = _status

    suspend fun getProducts() = productDao.getAllProducts().also {
        val resource = safeApiCall {
            productService.getProducts()
        }
        if (resource is NetworkResource.Success) {
            withContext(Dispatchers.Main) {
                _status.value = Event(true)
            }
            productDao.insertMultipleProducts(resource.value)
        } else if (resource is NetworkResource.Failure) {
            withContext(Dispatchers.Main) {
                _status.value = Event(false)
            }

        }
    }


}