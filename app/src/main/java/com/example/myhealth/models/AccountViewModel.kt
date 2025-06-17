package com.example.myhealth.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhealth.data.Person
import com.example.myhealth.room.HealthRoomDb
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val mainDB: HealthRoomDb
) : ViewModel() {
    var person = MutableLiveData<Person>()
    var inSystem = MutableStateFlow(false)
    var registrationDialog = MutableLiveData(false)


    private fun getPerson() {
        viewModelScope.launch {
            runCatching { mainDB.personDao.getPerson() }
                .onSuccess {
                    person.value = it
                    inSystem.value = true
                    registrationDialog.value = false
                }
                .onFailure {
                    inSystem.value = false
                    registrationDialog.value = true
                }
        }
    }

    private fun setPerson(person: Person) = viewModelScope.launch {
        mainDB.personDao.addPerson(person)
        getPerson()
    }

    fun delPerson(id: Int) = viewModelScope.launch {
        mainDB.personDao.deletePerson(id)
        inSystem.value = false
        showRegDialog()
    }

    init {
        getPerson()
    }

    fun showRegDialog(value: Boolean = true) {
        registrationDialog.value = value
    }

    fun setPersonDate(p: Person) {
        this.person.value = p
        setPerson(p)
        inSystem.value = true
        showRegDialog(false)
    }
}