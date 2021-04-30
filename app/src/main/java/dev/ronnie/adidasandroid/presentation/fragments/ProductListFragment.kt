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

        if (!hasBeenInitialized) {
            loadData()
            hasBeenInitialized = true
        }
        listenToResults()
        setSearchView()
        binding.errorText.setOnClickListener {
            loadData()
        }
    }

    private fun listenToResults() {
        viewModel.result.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let { resource ->
                binding.errorText.isVisible = resource is NetworkResource.Failure
                binding.progressCircular.isVisible = resource is NetworkResource.Loading

                if (resource is NetworkResource.Success) {
                    viewModel.saveData(resource.value)
                }

            }
        })
    }

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

    private fun navigateToDetails(product: Product) {
        binding.root.findNavController()
            .navigate(ProductListFragmentDirections.toProductDetailFragment(product))

    }

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

    private fun removeFocus() {
        hideSoftKeyboard()
        binding.searchView.isFocusable = false
    }
}