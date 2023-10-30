package com.example.shopapp.data.room

import android.content.Context
import android.icu.util.ULocale.Category
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shopapp.data.model.CategoryModel
import com.example.shopapp.data.model.ProductModel

@Database(entities = [CategoryModel::class, ProductModel::class], version = 1)
abstract class AppDatabase:RoomDatabase() {

    abstract val productDao : ProductDao
    abstract val categoryDao : CategoryDao

    companion object{

        @Volatile
        private var INSTANCE : AppDatabase? = null
        fun getInstance(context:Context):AppDatabase{
            synchronized(this){
                var instance  = INSTANCE
                instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java,
                    "database"
                ).build()
                return instance
            }
        }
    }
}