package com.example.shopapp.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.data.model.CategoryModel
import com.example.shopapp.repository.CategoryRepository
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {

    val categories = categoryRepository.categories

    fun startInsert(nameCategory:String) {
        insert(CategoryModel(0, nameCategory))
    }

    fun startUpdateCategory(idCategory:Int, nameCategory:String) {
        updateCategory(CategoryModel(idCategory, nameCategory))
    }

    fun insert(categoryModel: CategoryModel) = viewModelScope.launch{

        categoryRepository.insertCategory(categoryModel)
    }

    fun updateCategory(categoryModel: CategoryModel) = viewModelScope.launch{

        categoryRepository.updateCategory(categoryModel)
    }

    fun delete(categoryModel: CategoryModel) = viewModelScope.launch{

        categoryRepository.deleteCategory(categoryModel)
    }

    fun deleteAll() = viewModelScope.launch{

        categoryRepository.deleteAllCategories()
    }


}