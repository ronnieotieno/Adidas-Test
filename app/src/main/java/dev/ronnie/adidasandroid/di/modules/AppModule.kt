package dev.ronnie.adidasandroid.di.modules

import dagger.Module


/**
 *  A [Module] scoped to the Application class
 */
@Module(
    includes = [
        ViewModelModule::class,
        NetworkModule::class,
        DataModule::class
    ]
)
class AppModule