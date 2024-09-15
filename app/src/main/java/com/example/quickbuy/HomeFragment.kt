package com.example.quickbuy

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.example.quickbuy.data.products.Product
import com.example.quickbuy.ui.ItemSpacingDecoration
import com.example.quickbuy.ui.MainViewModel
import com.example.quickbuy.ui.ProductDetails
import com.example.quickbuy.ui.ProductsAdapter
import com.example.quickbuy.category.CategoriesAdapter
import com.example.quickbuy.category.CategoryDetailsActivity
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: ProductsAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var bannerViewPager: ViewPager2
    private lateinit var dotsIndicator: DotsIndicator
    private lateinit var categoryAdapter: CategoriesAdapter
    private lateinit var categoryRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // Initialize views
        recyclerView = view.findViewById(R.id.productRecyclerView)
        progressBar = view.findViewById(R.id.progressBar)
        bannerViewPager = view.findViewById(R.id.bannerViewPager)
        dotsIndicator = view.findViewById(R.id.dotsIndicator)
        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView)


        // Setup RecyclerView
        setupRecyclerView()

        // Handle loading state
        handleLoading()

        // Observe product data
        observeProducts()

        // Observe category data
        observeCategories()

        // Setup the banner with images and dots indicator
        setupBanner()
    }

    private fun setupRecyclerView() {
        // Set up RecyclerView with GridLayoutManager and item decoration
//        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.addItemDecoration(ItemSpacingDecoration(horizontal = 8, vertical = 1))
        recyclerView.setPadding(0, 0, 0, 80)

        // Setting up the RecyclerView for categories with a LinearLayoutManager (Horizontal scrolling)
        categoryRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun observeProducts() {
        // Collect product data from ViewModel
        lifecycleScope.launch {
            viewModel.productsResponse.collect { response ->
                if (response != null) {
                    adapter = ProductsAdapter(response) { item ->
                        navigateToDetails(item)
                    }
                    recyclerView.adapter = adapter
                }
            }
        }
    }

    private fun observeCategories() {
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
        // Navigate to ProductDetails activity
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
        // Observe loading state from ViewModel and update progress bar visibility
        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }

    //Banner
    private fun setupBanner() {
        // Sample banner images
        val imageUrls = listOf(
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3
        )

        // Prepare list of ImageView for ViewPager2
        val imageViews = imageUrls.map { imageUrl ->
            ImageView(requireContext()).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                scaleType = ImageView.ScaleType.FIT_XY
                load(imageUrl) {
                    crossfade(true)
                    placeholder(R.drawable.placeholder_image)
                }
            }
        }

        // Set up the ViewPager2 with custom adapter
        bannerViewPager.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                return object : RecyclerView.ViewHolder(imageViews[viewType]) {}
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                // No additional binding needed as images are already loaded
            }

            override fun getItemCount(): Int = imageUrls.size

            override fun getItemViewType(position: Int): Int = position
        }

        // Attach the DotsIndicator to the ViewPager2
        dotsIndicator.setViewPager2(bannerViewPager)
    }
}
