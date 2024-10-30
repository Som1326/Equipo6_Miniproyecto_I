package com.example.miniproyectoi.model

import com.google.gson.annotations.SerializedName
data class PokemonResponse(
    val pokemon: List<ProductModelResponse>
)

data class ProductModelResponse(
    @SerializedName("id")
    val id:Int,

    @SerializedName("img")
    val img:String
)
