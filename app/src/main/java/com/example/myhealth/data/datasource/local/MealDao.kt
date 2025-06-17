package com.example.myhealth.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myhealth.data.datasource.local.entity.MealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)

    suspend fun insert(meal: MealEntity)

    @Query("SELECT * FROM meals WHERE date(time/1000, 'unixepoch', 'localtime') = date('now', 'localtime')")

    fun getMealsForToday(): Flow<List<MealEntity>>

    // Калории за последние 7 дней (группировка по дням)
    @Query("""
        SELECT 
            strftime('%Y-%m-%d', datetime(time/1000, 'unixepoch', 'localtime')) as day,
            SUM(calories) as totalCalories
        FROM meals
        WHERE time >= :from
        GROUP BY day
        ORDER BY day DESC
        LIMIT 7
    """)
    fun getLast7DaysCalories(from: Long): Flow<List<MealDayStat>>

    // Сумма калорий за сегодня
    @Query("""
        SELECT SUM(calories) FROM meals
        WHERE date(time/1000, 'unixepoch', 'localtime') = date('now', 'localtime')
    """)
    fun getTodayCalories(): Flow<Int?>

    // БЖУ за сегодня
    @Query("""
        SELECT SUM(proteins) FROM meals WHERE date(time/1000, 'unixepoch', 'localtime') = date('now', 'localtime')
    """)
    fun getTodayProteins(): Flow<Int?>

    @Query("""
        SELECT SUM(fats) FROM meals WHERE date(time/1000, 'unixepoch', 'localtime') = date('now', 'localtime')
    """)
    fun getTodayFats(): Flow<Int?>

    @Query("""
        SELECT SUM(carbs) FROM meals WHERE date(time/1000, 'unixepoch', 'localtime') = date('now', 'localtime')
    """)
    fun getTodayCarbs(): Flow<Int?>

}

// Модель для статистики еды по дням
data class MealDayStat(
    val day: String,
    val totalCalories: Int
)