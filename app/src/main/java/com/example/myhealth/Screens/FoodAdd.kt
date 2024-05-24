package com.example.myhealth.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myhealth.data.Product
import com.example.myhealth.data.ProductType
import com.example.myhealth.ui.theme.MyHealthTheme

@Composable
fun FoodAdd(eatingName: String, modifier: Modifier = Modifier.padding(8.dp)) {

    Column {
        //TODO выбор времени
        Text(
            text = "$eatingName!",
            modifier = modifier
        )
        FoodSection()
        FoodDetailDialog()
    }
}

@Composable
fun FoodSection() {
   // FoodSectionItem()
}

@Composable
fun FoodSectionItem(productType: ProductType, onClickItem: () -> Unit) {
    Column (modifier = Modifier.background(color = Color.Transparent, shape = RoundedCornerShape(8.dp)), ){
        Text(stringResource(productType.name), modifier = Modifier)
    }
}

@Composable
fun FoodDetailDialog() {

}

@Preview(showBackground = true)
@Composable
fun FoodAddPreview() {
    MyHealthTheme {
        //FoodAdd("FoodAdd")
        FoodSectionItem(Product.Soup, { })
    }
}