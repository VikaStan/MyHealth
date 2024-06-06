package com.example.myhealth.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.myhealth.data.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor() : ViewModel() {
    lateinit var person: LiveData<Person>
    var inSystem = MutableStateFlow(false)
    var registrationDialog = MutableStateFlow(false)

    lateinit var updatePerson: (Person) -> Unit
    fun getData(model: MainScreenViewModel) {
        updatePerson= model::updatePersonDate
        inSystem = model.inSystem
        if (model.inSystem.value) {
            model.updatePersonDate(model.preferencesManager.getPerson())
            this.person = model.person
        } else {
            showRegDialog()
        }
    }
    fun showRegDialog(value: Boolean=true){
        registrationDialog.value=value
    }
    fun setPersonDate(person: Person){
        this.person= liveData { person }
        updatePerson(person)
        inSystem.value=true
        showRegDialog(false)
    }
}