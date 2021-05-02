package dev.ronnie.adidasandroid

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dev.ronnie.adidasandroid.di.DaggerAppComponent

/**
 * The application extends [DaggerApplication] which provide the necessary codes for the Dagger initialization, less boiler plate code.
 */
class AdidasTestApp : DaggerApplication() {
    private val applicationInjector = DaggerAppComponent.builder().application(this).build()
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = applicationInjector
}