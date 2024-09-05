package com.example.myhealth.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myhealth.R
import com.example.myhealth.data.Food
import com.example.myhealth.data.FoodTimeType
import com.example.myhealth.data.ProductOld
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class FoodAddViewModel @Inject constructor() : ViewModel() {

    var selectedTypeProductOld = MutableStateFlow(ProductOld())
    var foodAddDialog by mutableStateOf(false)
    var foodEditDialog by mutableStateOf(false)
    var eatingTimeName = R.string.breakfast_title
    var eatingFoodTime =
        MutableStateFlow(
            Food(
                productOlds = emptyList<ProductOld>().toMutableList(),
                foodTimeType = FoodTimeType.Dinner
            )
        )
    var productOlds = mutableStateListOf<ProductOld>()
    private var editProductIndex by mutableIntStateOf(0)

    var isChange = false

    fun getEatingTimeName(eatingType: String?) {
        when (eatingType) {
            FoodTimeType.Lunch.n -> eatingTimeName = R.string.lunch_title
            FoodTimeType.Breakfast.n -> eatingTimeName = R.string.breakfast_title
            FoodTimeType.Dinner.n -> eatingTimeName = R.string.dinner_title
            else -> eatingTimeName = R.string.lunch_title
        }
    }

    fun getEatingFoodTime(model: DiaryViewModel, eatingType: String?) {
        when (eatingType) {
            FoodTimeType.Lunch.n -> eatingFoodTime.value = model.selectedDayOld.value.lunch
            FoodTimeType.Breakfast.n -> eatingFoodTime.value = model.selectedDayOld.value.breakfast
            FoodTimeType.Dinner.n -> eatingFoodTime.value = model.selectedDayOld.value.dinner
            else -> eatingFoodTime.value = model.selectedDayOld.value.lunch
        }
        eatingFoodTime.value.productOlds.forEach {
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

    fun onProductItemSelected(productOld: ProductOld) {
        selectedTypeProductOld.value = productOld
        foodAddDialogShow()
    }

    fun onDelSwipe(productOld: ProductOld) {
        productOlds.remove(productOld)
        eatingFoodTime.value.productOlds.remove(productOld)
    }

    fun onEditSwipe(productOld: ProductOld) {
        editProductIndex = eatingFoodTime.value.productOlds.indexOf(productOld)
        selectedTypeProductOld.value = productOld
        foodEditDialog = true
    }

    fun addProduct(productOld: ProductOld) {
        isChange = true
        eatingFoodTime.value.productOlds.add(productOld)
        foodAddDialogShow(false)
    }

    fun editProduct(productOld: ProductOld) {
        isChange = true
        eatingFoodTime.value.productOlds[editProductIndex] = productOld
        productOlds[editProductIndex] = productOld
        foodEditDialogShow(false)
    }

    fun updateListProducts(model: DiaryViewModel) {
        if (isChange) {
            when (model.selectedEatTimeName.value) {
                FoodTimeType.Lunch.n -> model.selectedDayOld.value.lunch.productOlds = productOlds
                FoodTimeType.Breakfast.n -> model.selectedDayOld.value.breakfast.productOlds = productOlds
                FoodTimeType.Dinner.n -> model.selectedDayOld.value.dinner.productOlds = productOlds
                else -> model.selectedDayOld.value.lunch.productOlds = productOlds
            }
            model.selectedDayOld.value.updateAllCount()
            isChange = false
        }
    }


}