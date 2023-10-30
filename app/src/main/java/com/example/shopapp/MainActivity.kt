package com.example.shopapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.shopapp.databinding.ActivityMainBinding
import com.example.shopapp.fragment.TabCategoriesFragment
import com.example.shopapp.fragment.TabFiltersFragment
import com.example.shopapp.fragment.TabPanelFragment
import com.example.shopapp.fragment.TabProductFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.content, TabPanelFragment())

        binding.bottomNav.setOnNavigationItemSelectedListener(this)
        binding.bottomNav.selectedItemId = R.id.panelItemBottomNav

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.panelItemBottomNav -> supportFragmentManager.beginTransaction().replace(R.id.content, TabPanelFragment()).commit()
            R.id.catalogProductsItemBottomNav -> supportFragmentManager.beginTransaction().replace(R.id.content, TabProductFragment()).commit()
            R.id.catalogClothesItemBottomNav -> supportFragmentManager.beginTransaction().replace(R.id.content, TabFiltersFragment()).commit()
            R.id.catalogCategoriesItemBottomNav -> supportFragmentManager.beginTransaction().replace(R.id.content, TabCategoriesFragment()).commit()
        }

        return true
    }
}