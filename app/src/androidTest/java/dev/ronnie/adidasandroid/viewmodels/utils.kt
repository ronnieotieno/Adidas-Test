package dev.ronnie.adidasandroid.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.test.espresso.base.MainThread
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.ronnie.adidasandroid.data.entities.Product
import dev.ronnie.adidasandroid.utils.LOCAL_HOST
import okhttp3.OkHttpClient
import org.junit.runner.manipulation.Ordering
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream
import java.io.InputStreamReader
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.jvm.Throws

fun providesProductRetrofit(): Retrofit {
    val client = OkHttpClient()
    return Retrofit.Builder()
        .baseUrl("$LOCAL_HOST:3001/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun providesReviewRetrofit(): Retrofit {
    val client = OkHttpClient()
    return Retrofit.Builder()
        .baseUrl("$LOCAL_HOST:3002/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideProducts(context: Context): List<Product> {
    val type = object : TypeToken<List<Product>>() {}.type
    val jsonStream: InputStream = context.resources.assets.open("response.json")

    return Gson().fromJson(InputStreamReader(jsonStream), type)
}

//GetLive data value
@Throws(InterruptedException::class)
fun <T> getValue(liveData: LiveData<T>): T {
    val data = arrayOfNulls<Any>(1)
    val latch = CountDownLatch(1)
    liveData.observeForever { o ->
        data[0] = o
        latch.countDown()
    }
    latch.await(2, TimeUnit.SECONDS)

    @Suppress("UNCHECKED_CAST")
    return data[0] as T
}
