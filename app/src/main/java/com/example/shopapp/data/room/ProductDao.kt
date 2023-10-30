package com.example.shopapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shopapp.data.model.ProductModel

@Dao
interface ProductDao {

    @Insert
    fun insertProduct(productModel: ProductModel)

    @Update
    fun updateProduct(productModel: ProductModel)

    @Delete
    fun deleteProduct(productModel: ProductModel)

    @Query("DELETE  FROM product_data_table")
    fun deleteAllProducts()

    @Query("SELECT * FROM product_data_table")
    fun getAllProducts():LiveData<List<ProductModel>>

    @Query("SELECT * FROM product_data_table WHERE product_category = :nameCategory AND product_price = :priceProduct")
    fun getFilter(nameCategory:String, priceProduct:String):LiveData<List<ProductModel>>
}