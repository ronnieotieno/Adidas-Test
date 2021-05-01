package dev.ronnie.adidasandroid.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import dagger.android.support.DaggerFragment
import dev.ronnie.adidasandroid.R
import dev.ronnie.adidasandroid.data.entities.Product
import dev.ronnie.adidasandroid.databinding.FragmentProductListBinding
import dev.ronnie.adidasandroid.presentation.adapters.ProductsAdapter
import dev.ronnie.adidasandroid.presentation.viewModels.ProductListViewModel
import dev.ronnie.adidasandroid.presentation.viewModels.ViewModelFactory
import dev.ronnie.adidasandroid.utils.NetworkResource
import dev.ronnie.adidasandroid.utils.hideSoftKeyboard
import dev.ronnie.adidasandroid.utils.toast
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductListFragment : DaggerFragment(R.layout.fragment_product_list) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ProductListViewModel by viewModels {
        viewModelFactory
    }
    private var job: Job? = null

    private val adapter = ProductsAdapter { product: Product -> navigateToDetails(product) }
    private var hasBeenInitialized = false

    private lateinit var binding: FragmentProductListBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProductListBinding.bind(view)

        binding.list.adapter = adapter


        //prevent data to be called multiple times when the fragment is resumed
        if (!hasBeenInitialized) {
            loadData()
            hasBeenInitialized = true
        }

        //Force fetching of data from the api
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchProducts()
        }
        listenToResults()
        setSearchView()
        binding.errorText.setOnClickListener {
            loadData()
        }
    }

    //observe data from the api
    private fun listenToResults() {
        viewModel.result.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let { resource ->

                binding.errorText.isVisible =
                    resource is NetworkResource.Failure && adapter.itemCount == 0
                binding.progressCircular.isVisible =
                    resource is NetworkResource.Loading && adapter.itemCount == 0

                if (resource is NetworkResource.Success) {
                    viewModel.saveData(resource.value)
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                if (resource is NetworkResource.Failure) binding.swipeRefreshLayout.isRefreshing =
                    false

            }
        })
    }

    /**
     * Loads data from the local cache, if empty it calls the api which then deposit the data to local db
     */
    private fun loadData() {
        job?.cancel()
        job = lifecycleScope.launch {
            viewModel.getProducts().observe(viewLifecycleOwner, {
                viewModel.productList = it as ArrayList<Product>
                adapter.setData(it)
                if (it.isNullOrEmpty()) {
                    viewModel.fetchProducts()
                }
            })
        }
    }

    //navigate sending the product
    private fun navigateToDetails(product: Product) {
        binding.root.findNavController()
            .navigate(ProductListFragmentDirections.toProductDetailFragment(product))

    }

    /**
     * Filter the list based on user query
     */

    @SuppressLint("ClickableViewAccessibility")
    private fun setSearchView() {
        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                adapter.filter(viewModel.productList, s.toString().trim())

                if (s.toString().trim().isEmpty()) {
                    removeFocus()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        binding.searchView.setOnTouchListener { v, _ ->
            v.isFocusableInTouchMode = true
            false
        }
        binding.searchView.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            removeFocus()
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                adapter.filter(viewModel.productList, binding.searchView.text.toString().trim())
                return@OnEditorActionListener true
            }
            false
        })
    }

    override fun onPause() {
        super.onPause()
        removeFocus()

    }

    //removes the focus from search view
    private fun removeFocus() {
        hideSoftKeyboard()
        binding.searchView.isFocusable = false
    }
}