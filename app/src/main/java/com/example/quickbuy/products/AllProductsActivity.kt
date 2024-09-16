package com.example.quickbuy.products

import android.content.Intent
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
import com.example.quickbuy.data.products.Product
import com.example.quickbuy.data.products.ProductsResponse
import com.example.quickbuy.ui.MainViewModel
import kotlinx.coroutines.launch

class AllProductsActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_all_products)

        // Initialize the adapter with an empty list initially
        productsAdapter = ProductsAdapter(ProductsResponse()) { product ->
            navigateToDetails(product)
        }

        // Set up RecyclerView
        recyclerView = findViewById(R.id.allProductsRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = productsAdapter

        // Observe product data
        observeProducts()
    }

    // Observe the products and update the adapter
    private fun observeProducts() {
        lifecycleScope.launch {
            viewModel.productsResponse.collect { response ->
                if (response != null) {
                    // Update adapter with new products
                    productsAdapter = ProductsAdapter(response) { item ->
                        navigateToDetails(item)
                    }
                    recyclerView.adapter = productsAdapter
                }
            }
        }
    }

    // Navigate to the ProductDetails screen
    private fun navigateToDetails(item: Product) {
        val intent = Intent(this, ProductDetails::class.java).apply {
            putExtra("PRODUCT", item) // Pass the product object
        }
        startActivity(intent)
    }
}
