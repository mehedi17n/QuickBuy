package com.example.quickbuy.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickbuy.api.ApiClient
import com.example.quickbuy.api.Resource
import com.example.quickbuy.data.Category
import com.example.quickbuy.data.products.ProductsResponse
import com.example.quickbuy.repository.ShopRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    // Saving ProductsResponse instead of the Resource wrapper
    val productsResponse = MutableStateFlow<ProductsResponse?>(null)

    // Categories state
    val categories = MutableStateFlow<List<String?>>(emptyList())

    // Separate variables to handle loading and error states
    val isLoading = MutableStateFlow(true)
    val errorMessage = MutableStateFlow<String?>(null)


    private val repository = ShopRepository(ApiClient.api)

    init {
        getProducts()
        getCategories()
    }

    private fun getProducts() {
        viewModelScope.launch {
            repository.getProducts(
                limit = 100,
                sort = "asc"
            ).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        isLoading.value = true // Set loading state
                    }

                    is Resource.Success -> {
                        isLoading.value = false // Stop loading
                        productsResponse.value = resource.data // Set products data

                    }

                    is Resource.Error -> {
                        isLoading.value = false // Stop loading
                        errorMessage.value = resource.message // Set error message
                    }
                }
            }
        }
    }

    // Fetch categories from repository
    private fun getCategories() {
        viewModelScope.launch {
            repository.getCategories().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        isLoading.value = true // Set loading state
                    }

                    is Resource.Success -> {
                        isLoading.value = false // Stop loading
                        categories.value = resource.data // Set categories data
                    }

                    is Resource.Error -> {
                        isLoading.value = false // Stop loading
                        errorMessage.value = resource.message // Set error message
                    }
                }
            }
        }
    }
}
