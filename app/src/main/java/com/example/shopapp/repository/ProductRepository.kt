package com.example.shopapp.repository

import androidx.lifecycle.LiveData
import com.example.shopapp.data.model.ProductModel
import com.example.shopapp.data.room.ProductDao

class ProductRepository (private val productDAO: ProductDao) {

    val products = productDAO.getAllProducts()

    fun getFilter (nameCategory:String, priceProduct:String):
            LiveData<List<ProductModel>> {
        return productDAO.getFilter(nameCategory, priceProduct)
    }


    suspend fun insertProduct(productModel: ProductModel){
        productDAO.insertProduct(productModel)
    }

    suspend fun updateProduct(productModel: ProductModel){
        productDAO.updateProduct(productModel)
    }

    suspend fun deleteProduct(productModel: ProductModel) {
        productDAO.deleteProduct(productModel)
    }

    suspend fun deleteAllProducts(){
        productDAO.deleteAllProducts()
    }
}