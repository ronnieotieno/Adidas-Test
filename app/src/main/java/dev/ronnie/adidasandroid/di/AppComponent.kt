package dev.ronnie.adidasandroid.di

import android.app.Application

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dev.ronnie.adidasandroid.AdidasTestApp
import dev.ronnie.adidasandroid.di.modules.AppModule
import dev.ronnie.adidasandroid.di.modules.MainActivityModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        MainActivityModule::class
    ]
)
/**
 * Generates [Component] which is install in the [Application] class.
 */
interface AppComponent : AndroidInjector<AdidasTestApp> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(app: AdidasTestApp)
}
