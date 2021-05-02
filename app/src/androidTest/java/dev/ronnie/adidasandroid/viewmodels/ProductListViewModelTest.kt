package dev.ronnie.adidasandroid.viewmodels

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import dev.ronnie.adidasandroid.MainActivity
import dev.ronnie.adidasandroid.api.ProductService
import dev.ronnie.adidasandroid.api.ReviewService
import dev.ronnie.adidasandroid.data.dao.ProductDao
import dev.ronnie.adidasandroid.data.db.AppDataBase
import dev.ronnie.adidasandroid.data.repositories.ProductRepository
import dev.ronnie.adidasandroid.presentation.viewModels.ProductListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.manipulation.Ordering


class ProductListViewModelTest {

    private lateinit var database: AppDataBase
    private lateinit var viewmodel: ProductListViewModel
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

        viewmodel = ProductListViewModel(repository)


    }

    @After
    fun tearDown() {
        database.clearAllTables()
        database.close()
    }

    @Test
    fun insert_products_returns_2()  = runBlocking{
        val products = provideProducts(context)
        //Populate Db
        viewmodel.saveData(products)

        ViewMatchers.assertThat(getValue(viewmodel.getProducts()).size, CoreMatchers.equalTo(2))
    }
}