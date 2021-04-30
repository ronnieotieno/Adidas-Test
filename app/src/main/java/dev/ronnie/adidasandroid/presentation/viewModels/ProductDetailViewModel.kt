package dev.ronnie.adidasandroid.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.ronnie.adidasandroid.data.entities.Product
import dev.ronnie.adidasandroid.data.entities.Review
import dev.ronnie.adidasandroid.data.repositories.ProductRepository
import dev.ronnie.adidasandroid.utils.Event
import dev.ronnie.adidasandroid.utils.NetworkResource
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductDetailViewModel @Inject constructor(private val repository: ProductRepository) :
    ViewModel() {

    var product: Product? = null

    private val _result: MutableLiveData<Event<NetworkResource<Review>>> =
        MutableLiveData()
    val result: LiveData<Event<NetworkResource<Review>>> get() = _result

    fun postReview(pair: Pair<String, Int>) = viewModelScope.launch {

        _result.value = Event(NetworkResource.Loading)

        val review = product?.id?.let { Review("", it, pair.second, pair.first) }

        _result.value = Event(repository.postReview(review!!))

    }

    fun getProduct(id: String) = repository.getSingleProduct(id)
    fun updateProduct(review: Review) = viewModelScope.launch {
        product?.let {
            val reviews = it.reviews.toMutableList()
            reviews.add(0, review)
            it.reviews = reviews
            repository.insertProduct(it)
        }


    }
}