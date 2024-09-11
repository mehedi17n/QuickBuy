package com.example.quickbuy

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.example.quickbuy.data.products.Product
import com.example.quickbuy.ui.ItemSpacingDecoration
import com.example.quickbuy.ui.MainViewModel
import com.example.quickbuy.ui.ProductDetails
import com.example.quickbuy.ui.ProductsAdapter
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: ProductsAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var bannerViewPager: ViewPager2
    private lateinit var dotsIndicator: DotsIndicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        recyclerView = view.findViewById(R.id.productRecyclerView)
        progressBar = view.findViewById(R.id.progressBar)
        bannerViewPager = view.findViewById(R.id.bannerViewPager)
        dotsIndicator = view.findViewById(R.id.dotsIndicator)

        // Setting up the RecyclerView with a GridLayoutManager
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.addItemDecoration(ItemSpacingDecoration(horizontal = 4, vertical = 16))
        recyclerView.setPadding(0, 0, 0, 80)

        handleLoading()

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

        // Set up the ViewPager2 and DotsIndicator
        setupBanner()
    }

    private fun navigateToDetails(item: Product) {
        // Navigating to the ProductDetails Activity with the selected Product data
        val intent = Intent(requireContext(), ProductDetails::class.java).apply {
            putExtra("PRODUCT", item)
        }
        startActivity(intent)
    }

    private fun handleLoading() {
        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                if (isLoading) {
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun setupBanner() {
        // Sample data for the ViewPager2
        val imageUrls = listOf(
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3
        )

        // Create a list of ImageViews for the ViewPager2
        val imageViews = imageUrls.map { url ->
            val imageView = ImageView(requireContext())
            imageView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            imageView.load(url) {
                crossfade(true)
                placeholder(R.drawable.placeholder_image) // Optional placeholder image
            }
            imageView
        }

        // Set up the ViewPager2
        bannerViewPager.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                return object : RecyclerView.ViewHolder(imageViews[viewType]) {}
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                // No need to bind data as it's already set
            }

            override fun getItemCount(): Int = imageUrls.size

            override fun getItemViewType(position: Int): Int = position
        }

        // Link DotsIndicator with ViewPager2
        dotsIndicator.setViewPager2(bannerViewPager)
    }
}
