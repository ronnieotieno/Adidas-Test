package dev.ronnie.adidasandroid.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import dev.ronnie.adidasandroid.api.ProductService
import dev.ronnie.adidasandroid.api.ReviewService
import dev.ronnie.adidasandroid.data.dao.ProductDao
import dev.ronnie.adidasandroid.data.db.AppDataBase
import dev.ronnie.adidasandroid.data.entities.Product
import dev.ronnie.adidasandroid.data.repositories.ProductRepository
import javax.inject.Singleton

/**
 *  A [Module] to provide the [ProductDao] and [AppDataBase]
 */
@Module
object DataModule {

    @Provides
    @Singleton
    fun providesDB(app: Application): AppDataBase {
        return AppDataBase.invoke(app.applicationContext)
    }

    @Singleton
    @Provides
    fun providesDao(appDataBase: AppDataBase): ProductDao = appDataBase.productDao

    @Singleton
    @Provides
    fun providesRepository(
        productService: ProductService,
        productDao: ProductDao, reviewService: ReviewService
    ): ProductRepository =
        ProductRepository(productService, productDao, reviewService)

}