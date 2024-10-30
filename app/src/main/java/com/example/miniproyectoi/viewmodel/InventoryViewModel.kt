package com.example.miniproyectoi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope

import com.example.miniproyectoi.model.ProductModelResponse
import com.example.miniproyectoi.repository.InventoryRepository
import kotlinx.coroutines.launch

class InventoryViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application
    private val inventoryRepository = InventoryRepository(context)


    private val _listProducts = MutableLiveData<List<ProductModelResponse>>()
    val listProducts: LiveData<List<ProductModelResponse>> get() = _listProducts

    private val _progresState = MutableLiveData<Boolean>()
    val progresState: LiveData<Boolean> get() = _progresState

    fun getProducts() {
        viewModelScope.launch {
            _progresState.value = true
            try {
                _listProducts.value = inventoryRepository.getProducts()
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
                // Manejo de errores
            }
        }
    }
}
