package com.example.myhealth.domain.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BakeryDining
import androidx.compose.material.icons.filled.EggAlt
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Icecream
import androidx.compose.material.icons.filled.KebabDining
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.RiceBowl
import androidx.compose.material.icons.filled.SetMeal
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.filled.SoupKitchen
import androidx.compose.material.icons.filled.SportsRugby
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.myhealth.R

sealed class ProductType(
    val icon: ImageVector,
    val name: Int,
) {
    data object Bakery : ProductType(Icons.Default.BakeryDining, R.string.bakery)
    data object Porridge : ProductType(Icons.Default.RiceBowl, R.string.porridge)
    data object Soup : ProductType(Icons.Default.SoupKitchen, R.string.soup)
    data object Eggs : ProductType(Icons.Default.EggAlt, R.string.eggs)
    data object Meat : ProductType(Icons.Default.KebabDining, R.string.meat)
    data object Cheese : ProductType(Icons.Default.PieChart, R.string.cheese)
    data object Fruts : ProductType(Icons.Default.SportsRugby, R.string.fruts)
    data object Vegetables : ProductType(Icons.Default.ShoppingBasket, R.string.vegetables)
    data object Candies : ProductType(Icons.Default.Icecream, R.string.candies)
    data object Fish : ProductType(Icons.Default.SetMeal, R.string.fish)
    data object Snack : ProductType(Icons.Default.Fastfood, R.string.snack)
    data object OtherFood : ProductType(Icons.Default.Restaurant, R.string.other_food)
    data object Water : ProductType(Icons.Default.WaterDrop, R.string.water)
}
