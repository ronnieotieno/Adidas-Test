package dev.ronnie.adidasandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import dagger.android.support.DaggerAppCompatActivity
import dev.ronnie.adidasandroid.api.ProductService
import dev.ronnie.adidasandroid.data.repositories.ProductRepository
import dev.ronnie.adidasandroid.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setting the app theme when the activity is created, when the app starts, it starts with splash theme
        setTheme(R.style.Theme_AdidasAndroid)
        setContentView(R.layout.activity_main)

        //Host the fragments

    }
}