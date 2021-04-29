package dev.ronnie.adidasandroid.di.modules


import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.ronnie.adidasandroid.presentation.fragments.ProductDetailFragment
import dev.ronnie.adidasandroid.presentation.fragments.ProductListFragment

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeListFragment(): ProductListFragment

    @ContributesAndroidInjector
    abstract fun contributeXDetailFragment(): ProductDetailFragment

}
