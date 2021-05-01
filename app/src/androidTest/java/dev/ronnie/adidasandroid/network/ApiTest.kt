package dev.ronnie.adidasandroid.network

import android.content.Context
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import dev.ronnie.adidasandroid.api.ProductService
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

class ApiTest {
    private var context: Context? = null
    private var mockWebServer = MockWebServer()
    private lateinit var productService: ProductService

    @Before
    fun setup() {
        mockWebServer.start()

        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        context = InstrumentationRegistry.getInstrumentation().targetContext


        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        productService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ProductService::class.java)

        val jsonStream: InputStream = context!!.resources.assets.open("response.json")
        val jsonBytes: ByteArray = jsonStream.readBytes()


        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(String(jsonBytes))
        mockWebServer.enqueue(response)


    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun test_response() = runBlocking {
        val data = productService.getProducts()
        ViewMatchers.assertThat(data[0].name, CoreMatchers.equalTo("New Shoe"))
        ViewMatchers.assertThat(data[1].description, CoreMatchers.equalTo("Good for summer"))
        ViewMatchers.assertThat(data.size, CoreMatchers.equalTo(2))
    }

}