package dev.ronnie.adidasandroid.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.material.appbar.AppBarLayout
import dagger.android.support.DaggerFragment
import dev.ronnie.adidasandroid.R
import dev.ronnie.adidasandroid.databinding.FragmentProductDetailsBinding
import dev.ronnie.adidasandroid.presentation.adapters.RatingsAdapter
import dev.ronnie.adidasandroid.presentation.viewModels.ProductDetailViewModel
import dev.ronnie.adidasandroid.presentation.viewModels.ViewModelFactory
import dev.ronnie.adidasandroid.utils.NetworkResource
import dev.ronnie.adidasandroid.utils.makeSnackBar
import dev.ronnie.adidasandroid.utils.toast
import javax.inject.Inject
import kotlin.math.abs


class ProductDetailFragment : DaggerFragment(R.layout.fragment_product_details),
    RatingDialog.SendInterface {

    private lateinit var binding: FragmentProductDetailsBinding
    private val args = ProductDetailFragmentArgs
    private val adapter = RatingsAdapter()
    private val dialog = RatingDialog()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ProductDetailViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProductDetailsBinding.bind(view)
        setToolbar()
        setImageViewSize()

        //passed product
        val product = arguments?.let { args.fromBundle(it).product }


        //Bind data to the views
        binding.apply {
            binding.product = product
            isRound = false
        }

        binding.ratingList.adapter = adapter

        product?.let { observeReviews(it.id); setAppBar(it.name) }

        binding.addReview.setOnClickListener {
            dialog.show(childFragmentManager, null)

        }
    }

    /**
     * Inorder for the image to fit properly we need to get display size at runtime programmatically and set the imageview as square
     **/
    private fun setImageViewSize() {

        binding.image.layoutParams = ConstraintLayout.LayoutParams(
            resources.displayMetrics.widthPixels,
            resources.displayMetrics.widthPixels
        )

    }

    //setting up the toolbar
    private fun setToolbar() {
        val toolbar = binding.toolbar

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setHomeButtonEnabled(true)

        toolbar.setNavigationOnClickListener {
            binding.root.findNavController().navigateUp()
        }
    }

    //observe changes in the product
    private fun observeReviews(id: String) {
        viewModel.getProduct(id).observe(viewLifecycleOwner, {
            viewModel.product = it
            //set ids to reviews
            val reviews = viewModel.setIds(it.reviews)
            adapter.setData(reviews)

        })
    }

    private fun setAppBar(name: String) {
        //Listens to changes in the appbar visibility and change the toolbar title
        binding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { collapsingToolbar, verticalOffset ->

            when {
                abs(verticalOffset) - collapsingToolbar.totalScrollRange == 0 -> {
                    binding.toolbar.title = name
                }
                verticalOffset == 0 -> {
                    binding.toolbar.title = ""
                }
            }

        })
    }

    //send rating and observe the response
    override fun sendRating(rating: Pair<String, Int>) {
        val snackBar = makeSnackBar()
        viewModel.result.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let {
                when (it) {
                    is NetworkResource.Loading -> {
                        snackBar.show()
                    }
                    is NetworkResource.Failure -> {
                        snackBar.dismiss()
                        requireContext().toast("There was an error posting the review, please check your internet and try again")
                    }
                    is NetworkResource.Success -> {
                        snackBar.dismiss()
                        requireContext().toast("Review posted successfully")
                        viewModel.updateProduct(it.value)
                    }
                }

            }

        })
        viewModel.postReview(rating)
    }
}