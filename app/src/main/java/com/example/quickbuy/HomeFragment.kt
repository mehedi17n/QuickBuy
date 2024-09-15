package com.example.quickbuy

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quickbuy.data.products.Product
import com.example.quickbuy.ui.ItemSpacingDecoration
import com.example.quickbuy.ui.MainViewModel
import com.example.quickbuy.ui.ProductDetails
import com.example.quickbuy.ui.ProductsAdapter
import com.example.quickbuy.ui.CategoriesAdapter // Import the CategoriesAdapter
import com.example.quickbuy.ui.CategoryDetailsActivity
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var productRecyclerView: RecyclerView
    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var productAdapter: ProductsAdapter
    private lateinit var categoryAdapter: CategoriesAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // Initialize UI components
        productRecyclerView = view.findViewById(R.id.productRecyclerView)
        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView) // Initialize Category RecyclerView
        progressBar = view.findViewById(R.id.progressBar)

        // Setting up the RecyclerView for products with a GridLayoutManager
        productRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        productRecyclerView.addItemDecoration(ItemSpacingDecoration(horizontal = 4, vertical = 16))
        productRecyclerView.setPadding(0, 0, 0, 80)

        // Setting up the RecyclerView for categories with a LinearLayoutManager (Horizontal scrolling)
        categoryRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        handleLoading() // Handle loading state

        // Observe products data and set up the ProductAdapter
        lifecycleScope.launch {
            viewModel.productsResponse.collect { response ->
                if (response != null) {
                    productAdapter = ProductsAdapter(response) { item ->
                        navigateToDetails(item)
                    }
                    productRecyclerView.adapter = productAdapter
                }
            }
        }

        // Observe categories data and set up the CategoriesAdapter
        lifecycleScope.launch {
            viewModel.categories.collect { categories ->
                if (categories.isNotEmpty()) {
                    categoryAdapter = CategoriesAdapter(categories) { category ->
                        navigateToCategoryDetails(category.toString())
                    }
                    categoryRecyclerView.adapter = categoryAdapter
                }
            }
        }
    }

    private fun navigateToDetails(item: Product) {
        // Navigating to the ProductDetails Activity with the selected Product data
        val intent = Intent(requireContext(), ProductDetails::class.java).apply {
            putExtra("PRODUCT", item)
        }
        startActivity(intent)
    }

    private fun navigateToCategoryDetails(category: String) {
        // Navigating to the CategoryDetailsActivity with the selected Category data
        val intent = Intent(requireContext(), CategoryDetailsActivity::class.java).apply {
            putExtra("CATEGORY", category)
        }
        startActivity(intent)
    }

    private fun handleLoading() {
        // Handling loading state using ViewModel
        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }
}
