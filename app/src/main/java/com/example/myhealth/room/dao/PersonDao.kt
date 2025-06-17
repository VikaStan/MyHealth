package com.example.myhealth.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myhealth.data.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {
    @Query("SELECT * FROM person")
    suspend fun getPerson(): Person

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPerson(person: Person)

    @Update
    suspend fun updPerson(person: Person)

    @Query("DELETE FROM person WHERE personId = :id")
    suspend fun deletePerson(id: Int)

    @Query("SELECT * FROM person LIMIT 1")
    fun observePerson(): Flow<Person?>
}