package com.example.shopapp.fragment

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shopapp.R
import com.example.shopapp.data.room.AppDatabase
import com.example.shopapp.databinding.FragmentPanelEditCategoryBinding
import com.example.shopapp.repository.CategoryRepository
import com.example.shopapp.viewmodel.CategoryFactory
import com.example.shopapp.viewmodel.CategoryViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class PanelEditCategoryFragment :  BottomSheetDialogFragment(),View.OnKeyListener {

    private var binding: FragmentPanelEditCategoryBinding? = null
    private var categoryRepository: CategoryRepository? = null
    private var categoryViewModel: CategoryViewModel? = null
    private var factory: CategoryFactory? = null
    private var idCategory:Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPanelEditCategoryBinding.inflate(inflater, container, false)

        idCategory = arguments?.getString("idCategory")?.toInt()
        binding?.editCategory?.setText(arguments?.getString("nameCategory").toString())

        val categoriesDao = AppDatabase.getInstance((context as FragmentActivity).application).categoryDao
        categoryRepository = CategoryRepository(categoriesDao)
        factory = CategoryFactory(categoryRepository!!)
        categoryViewModel = ViewModelProvider(this,factory!!).get(CategoryViewModel::class.java)

        binding?.editCategory?.setOnKeyListener(this)

        return binding?.root
    }

    override fun onKey(view: View, i: Int, keyEvent: KeyEvent): Boolean {
        when (view.id) {


            R.id.editCategory -> {
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {

                    categoryViewModel?.startUpdateCategory(idCategory.toString().toInt(), binding?.editCategory?.text?.toString()!!)

                    binding?.editCategory?.setText("")

                    dismiss()

                    (context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.content, TabCategoriesFragment()).commit()

                    return true
                }

            }
        }

        return false
    }
}