package com.example.miniproyectoi.repository

import android.content.Context
import com.example.miniproyectoi.webservice.ApiService
import com.example.miniproyectoi.webservice.ApiUtils
import com.example.miniproyectoi.model.ProductModelResponse

class InventoryRepository (val context: Context) {
    private var apiService: ApiService = ApiUtils.getApiService()
    suspend fun getProducts(): List<ProductModelResponse> {
        val response = ApiUtils.getApiService().getProducts()
        return response.pokemon
    }
}