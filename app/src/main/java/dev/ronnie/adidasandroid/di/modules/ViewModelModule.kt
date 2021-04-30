package dev.ronnie.adidasandroid.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import dev.ronnie.adidasandroid.presentation.viewModels.ProductDetailViewModel
import dev.ronnie.adidasandroid.presentation.viewModels.ProductListViewModel
import dev.ronnie.adidasandroid.presentation.viewModels.ViewModelFactory
import kotlin.reflect.KClass


/**
 * A [Module] to provide the the [ProductDetailViewModel] and [ProductListViewModel]
 * That is created by [ViewModelFactory]
 * */
@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProductListViewModel::class)
    abstract fun bindListViewModel(productListViewModel: ProductListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductDetailViewModel::class)
    abstract fun bindDetailViewModel(productDetailViewModel: ProductDetailViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
