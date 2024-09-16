package com.example.quickbuy.category

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quickbuy.R
import com.example.quickbuy.ui.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AllCategoriesActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var categoryAdapter: CategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_categories)

        // Initialize the adapter with an empty list initially
        categoryAdapter = CategoriesAdapter(emptyList()) { category ->
            // Handle category click here, if needed
        }

        // Set up RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.allCategoriesRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 4)
        recyclerView.adapter = categoryAdapter

        // Fetch categories and update the adapter
        lifecycleScope.launch {
            viewModel.categories.collect { categories ->
                categoryAdapter = CategoriesAdapter(categories) { category ->
                    // Handle category click here, if needed
                }
                recyclerView.adapter = categoryAdapter
            }
        }
    }
}
