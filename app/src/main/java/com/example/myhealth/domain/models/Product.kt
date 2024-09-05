package com.example.myhealth.domain.models

class Product(
    val id: Int,
    val name: String,
    var caloriesPer100Gramm: Int,
    var gramms: Int,
    var caloriesPerGramm: Int,
    var description: String,
    ) {
}