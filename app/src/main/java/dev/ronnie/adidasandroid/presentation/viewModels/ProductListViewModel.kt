package dev.ronnie.adidasandroid.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.ronnie.adidasandroid.data.entities.Product
import dev.ronnie.adidasandroid.data.repositories.ProductRepository
import dev.ronnie.adidasandroid.utils.Event
import dev.ronnie.adidasandroid.utils.NetworkResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductListViewModel @Inject constructor(private val repository: ProductRepository) :
    ViewModel() {

    private val _result: MutableLiveData<Event<NetworkResource<List<Product>>>> =
        MutableLiveData()
    val result: LiveData<Event<NetworkResource<List<Product>>>> get() = _result

    var productList = ArrayList<Product>()

    //get all products from the local cache
    fun getProducts() = repository.getProducts()

    //fetch the products from the api
    fun fetchProducts() = viewModelScope.launch {
        _result.value = Event(NetworkResource.Loading)
        _result.value = Event(repository.fetchProducts())
    }

    //saves the data to local storage
    fun saveData(list: List<Product>) = viewModelScope.launch(Dispatchers.IO) {
        if (list.isNotEmpty()) {
            repository.saveData(list)
        }
    }

}