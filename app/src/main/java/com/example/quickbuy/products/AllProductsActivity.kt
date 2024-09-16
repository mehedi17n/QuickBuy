package com.example.quickbuy.products

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quickbuy.R
import com.example.quickbuy.data.products.ProductsResponse
import com.example.quickbuy.ui.MainViewModel
import kotlinx.coroutines.launch

class AllProductsActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_all_products)

        // Initialize the adapter with an empty list initially
        productsAdapter = ProductsAdapter(ProductsResponse()) { product ->
            // Handle product click here
        }

        // Set up RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.allProductsRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = productsAdapter

        // Fetch categories and update the adapter
        lifecycleScope.launch {
            viewModel.productsResponse.collect { response ->
                if (response != null) {
                    productsAdapter = ProductsAdapter(response) { item ->
                    }
                    recyclerView.adapter = productsAdapter
                }
            }
        }
    }
}
