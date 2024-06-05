package com.example.myhealth.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person")
class Person(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "personId")
    var id: Int = 0,
    val name: String = "",
    val age: Int = 0,
    val sex: String = "Male",
    val weight: Int = 0,
    val heigth: Int = 0,
    var sleepGoal: Float = 8f,
    var caloriesGoal: Double = 0.0
) {
    init {
        //Формула расчета калорийности Тома Венуто
        caloriesGoal = if (sex.equals("Male")) (66 + (13.7 * weight) + (5 * heigth) - (6.8 * age))
        else 665 + (9.6 * weight) + (1.8 * heigth) - (4.7 * age)
    }

}