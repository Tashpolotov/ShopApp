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
import com.example.shopapp.data.model.ProductModel
import com.example.shopapp.data.room.AppDatabase
import com.example.shopapp.databinding.FragmentTabProductBinding
import com.example.shopapp.repository.ProductRepository
import com.example.shopapp.viewmodel.ProductFactory
import com.example.shopapp.viewmodel.ProductViewModel


class TabProductFragment : Fragment() {

    private lateinit var binding:FragmentTabProductBinding
    private var productRepository: ProductRepository? = null
    private var productViewModel: ProductViewModel? = null
    private var productFactory: ProductFactory? = null
    private var productAdapter: ProductAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabProductBinding.inflate(inflater, container, false)

        val productDao = AppDatabase.getInstance((context as FragmentActivity).application).productDao
        productRepository = ProductRepository(productDao)
        productFactory = ProductFactory(productRepository!!)
        productViewModel = ViewModelProvider(this, productFactory!!).get(ProductViewModel::class.java)
        initRecyclerProducts()

        binding?.deleteAllProducts?.setOnClickListener(View.OnClickListener {
            productViewModel?.deleteAllProducts()
        })

        return binding?.root
    }

    private fun initRecyclerProducts(){
        binding?.recyclerProducts?.layoutManager = LinearLayoutManager(context)
        productAdapter = ProductAdapter({productModel: ProductModel ->deleteProduct(productModel)},
            {productModel: ProductModel ->editProduct(productModel)})
        binding?.recyclerProducts?.adapter = productAdapter

        displayProducts()
    }

    private fun displayProducts(){
        productViewModel?.products?.observe(viewLifecycleOwner, Observer {
            productAdapter?.setList(it)
            productAdapter?.notifyDataSetChanged()
        })
    }

    private fun deleteProduct(productModel:ProductModel) {
        productViewModel?.deleteProduct(productModel)
    }

    private fun editProduct(productModel:ProductModel) {
        val panelEditProduct = PanelEditProduct()
        val parameters = Bundle()
        parameters.putString("idProduct", productModel.id.toString())
        parameters.putString("nameProduct", productModel.name)
        parameters.putString("categoryProduct", productModel.category)
        parameters.putString("priceProduct", productModel.price)
        panelEditProduct.arguments = parameters

        panelEditProduct.show((context as FragmentActivity).supportFragmentManager, "editProduct")
    }

}