package com.example.quickbuy.repository

import android.util.Log
import com.example.quickbuy.api.Resource
import com.example.quickbuy.data.Category
import com.example.quickbuy.data.categories.CategoryResponse
import com.example.quickbuy.data.products.ProductsResponse
import com.example.quickbuy.service.ShopService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ShopRepository(private val api: ShopService) {

    // Fetch products
    suspend fun getProducts(
        limit: Number?,
        sort: String?
    ): Flow<Resource<ProductsResponse>> = flow {
        Log.d("Repository", "getProducts")
        try {
            emit(Resource.Loading) // Emit loading state
            val response = api.getProducts(
                limit,
                sort
            ) // Make the network request
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown Error")) // Emit error state
        }
    }.flowOn(Dispatchers.IO)


    // Fetch categories
    suspend fun getCategories(): Flow<Resource<CategoryResponse>> = flow {
        try {
            emit(Resource.Loading) // Emit loading state
            val response = api.getAllCategories()// Make the network request
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown Error")) // Emit error state
        }
    }.flowOn(Dispatchers.IO)


}