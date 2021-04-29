package dev.ronnie.adidasandroid.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.android.support.DaggerFragment
import dev.ronnie.adidasandroid.R
import dev.ronnie.adidasandroid.data.entities.Product
import dev.ronnie.adidasandroid.databinding.FragmentProductListBinding
import dev.ronnie.adidasandroid.presentation.adapters.ProductsAdapter
import dev.ronnie.adidasandroid.presentation.viewModels.ProductListViewModel
import dev.ronnie.adidasandroid.presentation.viewModels.ViewModelFactory
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

    private lateinit var binding: FragmentProductListBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProductListBinding.bind(view)

        binding.list.adapter = adapter

        loadData()
        listenToStatus()
        setSearchView()
        binding.errorText.setOnClickListener {
            loadData()
        }
    }

    private fun listenToStatus() {
        viewModel.networkStatus.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let { status ->
                binding.progressCircular.isVisible = false
                binding.errorText.isVisible = !status
            }
        })
    }

    private fun loadData() {
        job?.cancel()
        job = lifecycleScope.launch {
            binding.progressCircular.isVisible = true
            binding.errorText.isVisible = false
            viewModel.getProducts().observe(viewLifecycleOwner, {
                viewModel.productList = it as ArrayList<Product>
                adapter.setData(it)

                if (it.isNotEmpty()) {
                    binding.errorText.isVisible = false
                }

            })
        }
    }

    private fun navigateToDetails(product: Product) {
        requireContext().toast(product.toString())

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