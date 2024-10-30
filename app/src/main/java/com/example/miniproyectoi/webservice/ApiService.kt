package com.example.miniproyectoi.webservice

import com.example.miniproyectoi.model.PokemonResponse
import com.example.miniproyectoi.model.ProductModelResponse
import com.example.miniproyectoi.utils.Constants.END_POINT
import retrofit2.http.GET

interface ApiService {
    @GET(END_POINT)
    suspend fun getProducts(): PokemonResponse
}