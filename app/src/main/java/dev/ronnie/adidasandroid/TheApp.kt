package dev.ronnie.adidasandroid

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dev.ronnie.adidasandroid.di.DaggerAppComponent

class TheApp : DaggerApplication() {
    private val applicationInjector = DaggerAppComponent.builder().application(this).build()
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = applicationInjector
}