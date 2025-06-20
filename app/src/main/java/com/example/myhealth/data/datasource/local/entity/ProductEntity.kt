package com.example.myhealth.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myhealth.domain.models.Product

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    var caloriesPer100Gramm: Int,
    var proteinsPer100Gramm: Int,
    var fatsPer100Gramm: Int,
    var carbsPer100Gramm: Int,
    var gramms: Int,
    var caloriesPerGramm: Int,
    var description: String
) {
    fun toDomain() = Product(
        id,
        name,
        caloriesPer100Gramm,
        proteinsPer100Gramm,
        fatsPer100Gramm,
        carbsPer100Gramm,
        gramms,
        caloriesPerGramm,
        description
    )

    companion object {
        fun fromDomain(product: Product) = ProductEntity(
            id = product.id,
            name = product.name,
            caloriesPer100Gramm = product.caloriesPer100Gramm,
            proteinsPer100Gramm = product.proteinsPer100Gramm,
            fatsPer100Gramm = product.fatsPer100Gramm,
            carbsPer100Gramm = product.carbsPer100Gramm,
            gramms = product.gramms,
            caloriesPerGramm = product.caloriesPerGramm,
            description = product.description
        )
    }
}