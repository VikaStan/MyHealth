package com.example.myhealth.data

class Person(
    val name: String,
    val age: Int,
    val sex: String,
    weight: Float,
    heigth: Float,
    var sleepGoal: Float = 8f,
    var caloriesGoal: Double = 0.0
) {
    init {
        //Формула расчета калорийности Тома Венуто

        caloriesGoal = if (sex.equals("Male")) (66 + (13.7 * weight) + (5 * heigth) - (6.8 * age))
        else 665 + (9.6 *weight) + (1.8 * heigth) - (4.7 * age)
    }

}