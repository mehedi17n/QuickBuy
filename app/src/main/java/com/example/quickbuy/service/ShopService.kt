package com.example.quickbuy.service

import com.example.quickbuy.data.payloads.CreateProductPayload
import com.example.quickbuy.data.products.Product
import com.example.quickbuy.data.products.ProductsResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ShopService {
    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Number?,
        @Query("sort") sort: String?,
    ): ProductsResponse

    @GET("products/{id}")
    suspend fun getProductDetails(
        @Path(value = "id") id: String?,
    ): Product

    @GET("categories")
    suspend fun getAllCategories(): Product

    @GET("products/category/{categoryName}")
    suspend fun getProductsByCategory(
        @Path(value = "categoryName") categoryName: String?,
    ): Product


    @POST("products")
    suspend fun createProduct(
        @Body body: CreateProductPayload
    ): Product

    @PUT("products/{id}")
    suspend fun updateProduct(
        @Path(value = "id") id: String?,
        @Body body: CreateProductPayload
    ): Product


    @PATCH("products/{id}")
    suspend fun updateProductPatch(
        @Path(value = "id") id: String?,
        @Body body: CreateProductPayload
    ): Product

    @DELETE("products/{id}")
    suspend fun deleteProduct(
        @Path(value = "id") id: String?,
    ): Product

}