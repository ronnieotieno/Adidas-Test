package dev.ronnie.adidasandroid.viewmodels

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import org.hamcrest.MatcherAssert.assertThat
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import dev.ronnie.adidasandroid.MainActivity
import dev.ronnie.adidasandroid.api.ProductService
import dev.ronnie.adidasandroid.api.ReviewService
import dev.ronnie.adidasandroid.data.dao.ProductDao
import dev.ronnie.adidasandroid.data.db.AppDataBase
import dev.ronnie.adidasandroid.data.entities.Review
import dev.ronnie.adidasandroid.data.repositories.ProductRepository
import dev.ronnie.adidasandroid.presentation.viewModels.ProductDetailViewModel
import dev.ronnie.adidasandroid.presentation.viewModels.ProductListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.manipulation.Ordering


class ProductDetailViewModelTest {

    private lateinit var database: AppDataBase
    private lateinit var viewmodel: ProductDetailViewModel
    private lateinit var productService: ProductService
    private lateinit var productDao: ProductDao
    private lateinit var reviewService: ReviewService
    private lateinit var repository: ProductRepository
    private lateinit var context: Context

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp(): Unit = runBlocking {
        context = InstrumentationRegistry.getInstrumentation().targetContext

        //create a "disposable" database
        database = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java).build()

        val productRetrofit = providesProductRetrofit()
        val reviewRetrofit = providesReviewRetrofit()
        productService = productRetrofit.create(ProductService::class.java)
        reviewService = reviewRetrofit.create(ReviewService::class.java)
        productDao = database.productDao

        repository = ProductRepository(productService, productDao, reviewService)

        viewmodel = ProductDetailViewModel(repository)


    }

    @After
    fun tearDown() {
        database.clearAllTables()
        database.close()
    }

    @Test
    fun updates_product_should_pass() = runBlocking {
        val products = provideProducts(context)
        //Populate Db
        productDao.insertMultipleProducts(products)

        //set product to viewmodel
        val updateProduct = products[0]
        viewmodel.product = updateProduct

        //get original size
        val originalReviewSize = updateProduct.reviews.size

        //add review/ update the product
        val addReview = Review("", updateProduct.id, 5, "I love it")

        viewmodel.updateProduct(addReview)

        //check if reviews size increased by 1
      assertThat(
            getValue(viewmodel.getProduct(updateProduct.id)).reviews.size,
            CoreMatchers.equalTo(originalReviewSize + 1)
        )
    }
}