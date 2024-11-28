package com.example.miniproyectoi.repository

import android.content.Context
import com.example.miniproyectoi.webservice.ApiService
import com.example.miniproyectoi.model.ProductModelResponse
import javax.inject.Inject

class InventoryRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getProducts(): List<ProductModelResponse> {
        val response = apiService.getProducts()
        return response.pokemon
    }
}