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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductDetailViewModel @Inject constructor(private val repository: ProductRepository) :
    ViewModel() {

    var product: Product? = null

    private val _result: MutableLiveData<Event<NetworkResource<Review>>> =
        MutableLiveData()
    val result: LiveData<Event<NetworkResource<Review>>> get() = _result

    /**
     * @param pair contains the rating plus the review text
     * check if product isn't null then construct review which is then posted to the api.
     */
    fun postReview(pair: Pair<String, Int>) = viewModelScope.launch {

        //posts loading
        _result.value = Event(NetworkResource.Loading)

        val review = product?.id?.let { Review("", it, pair.second, pair.first) }

        //posts the results can be success or failure
        _result.value = Event(repository.postReview(review!!))

    }

    /**
     * @param id for the id of the product to be queried from the db
     **/
    fun getProduct(id: String) = repository.getSingleProduct(id)

    /**
     * Upon successful postage of [Review] update the db, add the added review to list of reviews of the particular
     * product then update.
     */

    fun updateProduct(review: Review) = viewModelScope.launch(Dispatchers.IO) {
        product?.let {
            val reviews = it.reviews.toMutableList()
            reviews.add(0, review)
            it.reviews = reviews
            repository.insertProduct(it)
        }


    }

    fun setIds(reviews: List<Review>): List<Review> {
        return reviews.mapIndexed { index, review ->
            review.id = index.toString()
            review
        }

    }
}