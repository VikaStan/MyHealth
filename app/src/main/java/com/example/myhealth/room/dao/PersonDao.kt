package com.example.myhealth.room.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myhealth.data.Person

@Dao
interface PersonDao {
    @Query("SELECT * FROM person")
    fun getPerson(): LiveData<Person>

    @Insert
    fun addPerson(person: Person)

    @Update
    fun updPerson(person: Person)
    @Query("DELETE FROM person WHERE personId = :id")
    fun deletePerson(id:Int)

}