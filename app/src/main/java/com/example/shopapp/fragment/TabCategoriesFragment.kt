package com.example.shopapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopapp.R
import com.example.shopapp.data.model.CategoryModel
import com.example.shopapp.data.room.AppDatabase
import com.example.shopapp.databinding.FragmentTabCategoriesBinding
import com.example.shopapp.repository.CategoryRepository
import com.example.shopapp.viewmodel.CategoryFactory
import com.example.shopapp.viewmodel.CategoryViewModel


class TabCategoriesFragment : Fragment() {

    private lateinit var binding:FragmentTabCategoriesBinding
    private var categoryRepository: CategoryRepository? = null
    private var categoryViewModel: CategoryViewModel? = null
    private var categoryFactory: CategoryFactory? = null
    private var categoryAdapter: CategoryAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTabCategoriesBinding.inflate(inflater, container, false)
        val categoriesDao = AppDatabase.getInstance((context as FragmentActivity).application).categoryDao
        categoryRepository = CategoryRepository(categoriesDao)
        categoryFactory = CategoryFactory(categoryRepository!!)
        categoryViewModel = ViewModelProvider(this, categoryFactory!!).get(CategoryViewModel::class.java)

        initRecyclerCategories()
        displayCategories()

        binding?.deleteAllCategories?.setOnClickListener(View.OnClickListener {
            categoryViewModel?.deleteAll()
        })

        return  binding?.root
    }

    private fun initRecyclerCategories(){
        binding?.recyclerCategories?.layoutManager = LinearLayoutManager(context)
        categoryAdapter = CategoryAdapter({categoryModel: CategoryModel ->deleteCategory(categoryModel)},
            {categoryModel:CategoryModel->editCategory(categoryModel)})
        binding?.recyclerCategories?.adapter = categoryAdapter


    }

    private fun displayCategories(){
        categoryViewModel?.categories?.observe(viewLifecycleOwner, Observer {
            categoryAdapter?.setList(it)
            categoryAdapter?.notifyDataSetChanged()
        })
    }


    private fun deleteCategory(categoryModel: CategoryModel) {
        categoryViewModel?.delete(categoryModel)
    }

    private fun editCategory(categoryModel:CategoryModel) {
        val panelCategory = PanelEditCategoryFragment()
        val parameters = Bundle()
        parameters.putString("idCategory", categoryModel.id.toString())
        parameters.putString("nameCategory", categoryModel.name)
        panelCategory.arguments = parameters

        panelCategory.show((context as FragmentActivity).supportFragmentManager, "editCategory")
    }

}