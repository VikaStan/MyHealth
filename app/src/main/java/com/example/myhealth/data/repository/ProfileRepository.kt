package com.example.myhealth.data.repository

import com.example.myhealth.data.Person
import com.example.myhealth.presentation.account.ProfileState
import com.example.myhealth.room.dao.PersonDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val personDao: PersonDao
) {
    val profileFlow: Flow<ProfileState> = personDao.observePerson().map { person ->
        person?.let {
            ProfileState(
                name = it.name,
                age = it.age,
                height = it.height,
                weight = it.weight,
                waterTarget = it.waterGoal.toInt(),
                caloriesTarget = it.caloriesGoal.toInt()
            )
        } ?: ProfileState()
    }

    suspend fun getProfile(): ProfileState {
        return runCatching { personDao.getPerson() }.getOrNull()?.let { person ->
            ProfileState(
                name = person.name,
                age = person.age,
                height = person.height,
                weight = person.weight,
                waterTarget = person.waterGoal.toInt(),
                caloriesTarget = person.caloriesGoal.toInt()
            )
        } ?: ProfileState()
    }

    suspend fun saveProfile(state: ProfileState) {
        val existing = runCatching { personDao.getPerson() }.getOrNull()
        val person = Person(
            id = existing?.id,
            name = state.name,
            age = state.age,
            weight = state.weight,
            height = state.height,
            caloriesGoal = state.caloriesTarget.toFloat(),
            waterGoal = state.waterTarget.toFloat()
        )
        personDao.addPerson(person)
    }

    suspend fun deleteProfile() {
        runCatching { personDao.getPerson() }.getOrNull()?.id?.let { id ->
            personDao.deletePerson(id)
        }
    }

    suspend fun logout() {
        // For now, same as delete profile
        deleteProfile()
    }
}