package com.example.myhealth.domain.models

class MealTime(
    val id: Int,
    val name: String,
    var productCount: Int,
    var totalCalories: Int,
    val goalCalories: Int,
    var productList: MutableList<Product>
) {
}