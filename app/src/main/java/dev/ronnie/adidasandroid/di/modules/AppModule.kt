package dev.ronnie.adidasandroid.di.modules

import dagger.Module


@Module(
    includes = [
        ViewModelModule::class,
        NetworkModule::class,
        DataModule::class
    ]
)
class AppModule