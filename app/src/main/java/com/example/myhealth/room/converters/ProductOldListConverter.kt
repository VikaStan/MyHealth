package com.example.myhealth.room.converters

import androidx.room.TypeConverter
import com.example.myhealth.data.ProductOld
import com.example.myhealth.data.ProductOld.*
import org.json.JSONArray
import org.json.JSONObject

class ProductOldListConverter {
    @TypeConverter
    fun fromList(list: MutableList<ProductOld>): String {
        val array = JSONArray()
        list.forEach { product ->
            val obj = JSONObject()
            obj.put("category", product.productCategory.javaClass.simpleName)
            obj.put("caloriesPer100Gramms", product.caloriesPer100Gramms)
            obj.put("caloriesSummery", product.caloriesSummery)
            obj.put("gramms", product.gramms)
            obj.put("description", product.description)
            array.put(obj)
        }
        return array.toString()
    }

    @TypeConverter
    fun toList(data: String?): MutableList<ProductOld> {
        val list = mutableListOf<ProductOld>()
        if (data.isNullOrBlank()) return list
        val array = JSONArray(data)
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            val category = when (obj.getString("category")) {
                Bakery::class.simpleName -> Bakery
                Porridge::class.simpleName -> Porridge
                Soup::class.simpleName -> Soup
                Eggs::class.simpleName -> Eggs
                Meat::class.simpleName -> Meat
                Cheese::class.simpleName -> Cheese
                Fruts::class.simpleName -> Fruts
                Vegetables::class.simpleName -> Vegetables
                Candies::class.simpleName -> Candies
                Fish::class.simpleName -> Fish
                Snack::class.simpleName -> Snack
                OtherFood::class.simpleName -> OtherFood
                Water::class.simpleName -> Water
                else -> Bakery
            }
            val product = ProductOld(
                productCategory = category,
                caloriesPer100Gramms = obj.getInt("caloriesPer100Gramms"),
                caloriesSummery = obj.getDouble("caloriesSummery").toFloat(),
                gramms = obj.getInt("gramms"),
                description = obj.getString("description")
            )
            list.add(product)
        }
        return list
    }
}