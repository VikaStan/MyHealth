package com.example.myhealth.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myhealth.R
import com.example.myhealth.data.Food
import com.example.myhealth.data.FoodTimeType
import com.example.myhealth.data.Product
import com.example.myhealth.data.ProductType
import kotlinx.coroutines.flow.MutableStateFlow

class FoodAddViewModel : ViewModel() {

    var selectedTypeProduct = MutableStateFlow(Product())
    var foodAddDialog by mutableStateOf(false)
    var foodEditDialog by mutableStateOf(false)
    var eatingTimeName = R.string.breakfast_title
    var eatingFoodTime = MutableStateFlow( Food(emptyList<Product>().toMutableList(), FoodTimeType.Dinner))
    var editProductIndex by mutableIntStateOf(0)

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
            FoodTimeType.Lunch.n -> eatingFoodTime.value = model.selectedDay.value.lunch
            FoodTimeType.Breakfast.n -> eatingFoodTime.value = model.selectedDay.value.breakfast
            FoodTimeType.Dinner.n -> eatingFoodTime.value = model.selectedDay.value.dinner
            else -> eatingFoodTime.value = model.selectedDay.value.lunch
        }

    }

    fun foodAddDialogShow(show: Boolean = true) {
        foodAddDialog = show
    }
    fun foodEditDialogShow(show: Boolean = true) {
        foodEditDialog = show
    }

    fun onProductItemSelected(product: Product){
        selectedTypeProduct.value=product
        foodAddDialogShow()
    }

    fun onDelSwipe(product: Product){
        eatingFoodTime.value.products.remove(product)
    }

    fun onEditSwipe(product: Product){
        editProductIndex = eatingFoodTime.value.products.indexOf(product)
        selectedTypeProduct.value=product
        foodEditDialog=true
    }
    fun AddProduct(product: Product){
        eatingFoodTime.value.products.add(product)
        foodAddDialogShow(false)
    }

    fun EditProduct(product: Product){
        eatingFoodTime.value.products[editProductIndex] = product
        foodEditDialogShow(false)
    }



}