package com.example.myhealth.domain.models

class Product(
    val id: Int,
    val name: String,
    var caloriesPer100Gramm: Int,
    var proteinsPer100Gramm: Int = 0,
    var fatsPer100Gramm: Int = 0,
    var carbsPer100Gramm: Int = 0,
    var gramms: Int,
    var caloriesPerGramm: Int,
    var description: String,
    var productCategory: ProductType = ProductType.OtherFood,
) {
    val caloriesSummery: Float
        get() = (caloriesPer100Gramm.toFloat() / 100) * gramms
}