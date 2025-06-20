package com.example.myhealth.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhealth.R
import com.example.myhealth.domain.models.MealTime
import com.example.myhealth.domain.models.Product
import com.example.myhealth.domain.repository.ProductRepository
import com.example.myhealth.presentation.diary.MealType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodAddViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    var selectedTypeProductOld = MutableStateFlow(Product(0, "", 0, 0, 0, 0, 0, 0, ""))
    var foodAddDialog by mutableStateOf(false)
    var foodEditDialog by mutableStateOf(false)
    var eatingTimeName = R.string.breakfast_title
    var eatingFoodTime =
        MutableStateFlow(
            MealTime(
                id = 0,
                name = MealType.DINNER.value,
                productCount = 0,
                totalCalories = 0,
                goalCalories = 0,
                productList = mutableListOf()
            )
        )
    var productOlds = mutableStateListOf<Product>()
    private var editProductIndex by mutableIntStateOf(0)

    var isChange = false

    fun getEatingTimeName(eatingType: String?) {
        when (eatingType) {
            MealType.LUNCH.value -> eatingTimeName = R.string.lunch_title
            MealType.BREAKFAST.value -> eatingTimeName = R.string.breakfast_title
            MealType.DINNER.value -> eatingTimeName = R.string.dinner_title
            else -> eatingTimeName = R.string.lunch_title
        }
    }

    fun getEatingFoodTime(eatingType: String?) {
        val type = eatingType ?: MealType.DINNER.value
        eatingFoodTime.value = MealTime(
            id = 0,
            name = type,
            productCount = productOlds.size,
            totalCalories = productOlds.sumOf { it.caloriesPer100Gramm * it.gramms / 100 },
            goalCalories = 0,
            productList = productOlds.toMutableList()
        )
    }

    fun foodAddDialogShow(show: Boolean = true) {
        foodAddDialog = show
    }

    fun foodEditDialogShow(show: Boolean = true) {
        foodEditDialog = show
    }

    fun onProductItemSelected(productOld: Product) {
        viewModelScope.launch {
            val info = productRepository.findProduct(productOld.name)
            info?.productCategory = productOld.productCategory
            selectedTypeProductOld.value = info ?: productOld
            foodAddDialogShow()
        }
    }

    fun onDelSwipe(productOld: Product) {
        productOlds.remove(productOld)
        eatingFoodTime.value.productList.remove(productOld)
    }

    fun onEditSwipe(productOld: Product) {
        editProductIndex = eatingFoodTime.value.productList.indexOf(productOld)
        selectedTypeProductOld.value = productOld
        foodEditDialog = true
    }

    fun addProduct(productOld: Product) {
        isChange = true
        eatingFoodTime.value.productList.add(productOld)
        foodAddDialogShow(false)
    }

    fun editProduct(productOld: Product) {
        isChange = true
        eatingFoodTime.value.productList[editProductIndex] = productOld
        productOlds[editProductIndex] = productOld
        foodEditDialogShow(false)
    }

    fun updateListProducts() {
        isChange = false
    }


}