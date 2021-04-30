package dev.ronnie.adidasandroid.data.dao

import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.LiveData
import androidx.room.*
import dev.ronnie.adidasandroid.data.entities.Product

/**
 * Data Access Object, interacts to db and exposes data to the Repository
 *
 * Consist of [RoomDatabase] annotations as well raw [SQLiteDatabase] queries
 *
 * See more [Room Dao](https://developer.android.com/training/data-storage/room/accessing-data)
 */

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMultipleProducts(productList: List<Product>): List<Long>

    @Query("SELECT * FROM  products_table WHERE id=:id")
    fun getSingleProduct(id: String): LiveData<Product>

    @Query("SELECT * FROM products_table")
    fun getAllProducts(): LiveData<List<Product>>


    @Query("SELECT COUNT(id) FROM products_table")
    suspend fun countProducts(): Int


}