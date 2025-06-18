package com.example.myhealth.room.converters

import androidx.room.TypeConverter
import com.example.myhealth.domain.models.Product
import org.json.JSONArray
import org.json.JSONObject

class ProductListConverter {
    @TypeConverter
    fun fromList(list: MutableList<Product>): String {
        val array = JSONArray()
        list.forEach { product ->
            val obj = JSONObject()
            obj.put("id", product.id)
            obj.put("name", product.name)
            obj.put("caloriesPer100Gramm", product.caloriesPer100Gramm)
            obj.put("gramms", product.gramms)
            obj.put("caloriesPerGramm", product.caloriesPerGramm)
            obj.put("description", product.description)
            array.put(obj)
        }
        return array.toString()
    }

    @TypeConverter
    fun toList(data: String?): MutableList<Product> {
        val list = mutableListOf<Product>()
        if (data.isNullOrBlank()) return list
        val array = JSONArray(data)
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            val product = Product(
                id = obj.getInt("id"),
                name = obj.getString("name"),
                caloriesPer100Gramm = obj.getInt("caloriesPer100Gramm"),
                gramms = obj.getInt("gramms"),
                caloriesPerGramm = obj.getInt("caloriesPerGramm"),
                description = obj.getString("description")
            )
            list.add(product)
        }
        return list
    }
}