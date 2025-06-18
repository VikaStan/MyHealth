package com.example.myhealth.room.converters

import androidx.room.TypeConverter
import com.example.myhealth.domain.models.MealTime
import org.json.JSONArray
import org.json.JSONObject

class MealTimeListConverter {
    private val productConverter = ProductListConverter()

    @TypeConverter
    fun fromList(list: MutableList<MealTime>): String {
        val array = JSONArray()
        list.forEach { meal ->
            val obj = JSONObject()
            obj.put("id", meal.id)
            obj.put("name", meal.name)
            obj.put("productCount", meal.productCount)
            obj.put("totalCalories", meal.totalCalories)
            obj.put("goalCalories", meal.goalCalories)
            obj.put("productList", productConverter.fromList(meal.productList))
            array.put(obj)
        }
        return array.toString()
    }

    @TypeConverter
    fun toList(data: String?): MutableList<MealTime> {
        val list = mutableListOf<MealTime>()
        if (data.isNullOrBlank()) return list
        val array = JSONArray(data)
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            val meal = MealTime(
                id = obj.getInt("id"),
                name = obj.getString("name"),
                productCount = obj.getInt("productCount"),
                totalCalories = obj.getInt("totalCalories"),
                goalCalories = obj.getInt("goalCalories"),
                productList = productConverter.toList(obj.getString("productList"))
            )
            list.add(meal)
        }
        return list
    }
}