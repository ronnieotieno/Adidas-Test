package dev.ronnie.adidasandroid.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import dev.ronnie.adidasandroid.BuildConfig
import dev.ronnie.adidasandroid.api.ProductService
import dev.ronnie.adidasandroid.data.entities.Product
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {

    private val loggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Singleton
    @Provides
    fun providesOkhttp(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) builder.addInterceptor(loggingInterceptor)
        return builder.build()
    }

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): ProductService =
        retrofit.create(ProductService::class.java)


    @Singleton
    @Provides
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.28:3001/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}