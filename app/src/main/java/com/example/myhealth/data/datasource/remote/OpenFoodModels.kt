package com.example.myhealth.data.datasource.remote

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("products") val products: List<ProductDto>
)

data class ProductDto(
    @SerializedName("product_name") val name: String?,
    @SerializedName("nutriments") val nutriments: NutrimentsDto?
)

data class NutrimentsDto(
    @SerializedName("energy-kcal_100g") val energy: Float?,
    @SerializedName("proteins_100g") val proteins: Float?,
    @SerializedName("fat_100g") val fat: Float?,
    @SerializedName("carbohydrates_100g") val carbs: Float?
)