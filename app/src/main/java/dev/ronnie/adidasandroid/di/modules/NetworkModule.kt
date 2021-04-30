package dev.ronnie.adidasandroid.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import dev.ronnie.adidasandroid.BuildConfig
import dev.ronnie.adidasandroid.api.ProductService
import dev.ronnie.adidasandroid.api.ReviewService
import dev.ronnie.adidasandroid.data.entities.Product
import dev.ronnie.adidasandroid.utils.LOCAL_HOST
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * A [Module] to provide the the [Retrofit] and services [ReviewService], [ProductService]
 **/
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
    fun providesProductService(@Named("productRetrofit") retrofit: Retrofit): ProductService =
        retrofit.create(ProductService::class.java)

    @Singleton
    @Provides
    fun providesReviewService(@Named("reviewRetrofit") retrofit: Retrofit): ReviewService =
        retrofit.create(ReviewService::class.java)


    @Singleton
    @Provides
    @Named("productRetrofit")
    fun providesProductRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("$LOCAL_HOST:3001/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    @Named("reviewRetrofit")
    fun providesReviewRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("$LOCAL_HOST:3002/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}