package dev.ronnie.adidasandroid.di.modules


import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.ronnie.adidasandroid.MainActivity

/**
 * A [Module] to provide the the [MainActivity]
 **/
@Suppress("unused")
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(
        modules = [
            FragmentBuildersModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity
}
