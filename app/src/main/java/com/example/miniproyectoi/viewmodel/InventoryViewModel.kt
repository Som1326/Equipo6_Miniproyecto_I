package com.example.miniproyectoi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.miniproyectoi.model.ProductModelResponse
import com.example.miniproyectoi.repository.InventoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val inventoryRepository: InventoryRepository
):ViewModel(){

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
