package com.example.quickbuy.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quickbuy.R
import kotlinx.coroutines.launch

class AllCategoriesActivity : AppCompatActivity() {
//
//    private val viewModel: MainViewModel by viewModels()
//    private lateinit var categoryAdapter: CategoriesAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_all_categories)
//
//        categoryAdapter = CategoriesAdapter(emptyList()) { category ->
//            // Handle category click
//        }
//
//        findViewById<RecyclerView>(R.id.allCategoriesRecyclerView).apply {
//            layoutManager = GridLayoutManager(this@AllCategoriesActivity, 2)
//            adapter = categoryAdapter
//        }
//
//        // Fetch categories and update the adapter
//        lifecycleScope.launch {
//            viewModel.categories.collect { categories ->
//                categoryAdapter.updateCategories(categories)
//            }
//        }
//    }
}