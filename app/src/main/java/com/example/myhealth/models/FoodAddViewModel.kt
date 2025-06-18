package com.example.myhealth.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myhealth.R
import com.example.myhealth.domain.models.MealTime
import com.example.myhealth.domain.models.Product
import com.example.myhealth.presentation.diary.DiaryScreenViewModel
import com.example.myhealth.presentation.diary.MealType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class FoodAddViewModel @Inject constructor() : ViewModel() {

    var selectedTypeProductOld = MutableStateFlow(Product(0, "", 0, 0, 0, ""))
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

    fun getEatingFoodTime(model: DiaryScreenViewModel, eatingType: String?) {
        when (eatingType) {
            MealType.LUNCH.value -> eatingFoodTime.value =
                model.selectedDayOld.value.mealTimeList[1]

            MealType.BREAKFAST.value -> eatingFoodTime.value =
                model.selectedDayOld.value.mealTimeList[0]

            MealType.DINNER.value -> eatingFoodTime.value =
                model.selectedDayOld.value.mealTimeList[2]

            else -> eatingFoodTime.value = model.selectedDayOld.value.mealTimeList[1]
        }
        eatingFoodTime.value.productList.forEach {
            if (!productOlds.contains(it))
                productOlds.add(it)
        }
    }

    fun foodAddDialogShow(show: Boolean = true) {
        foodAddDialog = show
    }

    fun foodEditDialogShow(show: Boolean = true) {
        foodEditDialog = show
    }

    fun onProductItemSelected(productOld: Product) {
        selectedTypeProductOld.value = productOld
        foodAddDialogShow()
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

    fun updateListProducts(model: DiaryScreenViewModel) {
        if (isChange) {
            when (model.selectedEatTimeName.value) {
                MealType.LUNCH.value -> model.selectedDayOld.value.mealTimeList[1].productList =
                    productOlds

                MealType.BREAKFAST.value -> model.selectedDayOld.value.mealTimeList[0].productList =
                    productOlds

                MealType.DINNER.value -> model.selectedDayOld.value.mealTimeList[2].productList =
                    productOlds

                else -> model.selectedDayOld.value.mealTimeList[1].productList = productOlds
            }
            model.selectedDayOld.value.updateAllCount()
            isChange = false
        }
    }


}