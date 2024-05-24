package com.example.myhealth.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myhealth.data.Product
import com.example.myhealth.data.ProductType
import com.example.myhealth.models.DiaryViewModel
import com.example.myhealth.ui.theme.MyHealthTheme

@Composable
fun FoodAdd(eatingName: String, modifier: Modifier, model: DiaryViewModel) {

    val selectedDay by model.selectedDay.collectAsState()

    Column {
        //TODO выбор времени
        Text(
            text = "$eatingName!", modifier = Modifier
        )
        FoodSection({})
        FoodDetailDialog()
        //TODO список выбранных продуктов
    }
}

@Composable
fun FoodSection(onClickItem: () -> Unit) { //TODO подсвечивать выбранную категорию
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(8.dp)
            .background(Color.LightGray.copy(0.35f), shape = RoundedCornerShape(8.dp)),
        horizontalArrangement = Arrangement.Center
    ) {
        listOf(
            Product.Eggs,
            Product.Soup,
            Product.Fish,
            Product.Meat,
            Product.Bakery,
            Product.Candies,
            Product.Cheese,
            Product.Fruts,
            Product.Porridge,
            Product.Snack,
            Product.Vegetables,
            Product.Water,
            Product.OtherFood,
        ).forEach {
            item {

                Box(contentAlignment = Alignment.TopEnd) {

                    FoodSectionItem(it, onClickItem = onClickItem)
                   /* Icon(Icons.Default.Cancel,
                        "",
                        modifier = Modifier.padding(top = 6.dp, end = 8.dp).clip(
                            CircleShape
                        ).clickable { })*/
                }

            }

        }
    }
}

@Composable
fun FoodSectionItem(productType: ProductType, onClickItem: () -> Unit) {
    Column(
        modifier = Modifier.background(
            color = Color.Transparent,
            shape = RoundedCornerShape(8.dp)
        )
            .fillMaxWidth().padding(4.dp)
            .border(2.dp, color = Color.Black, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp)).clickable { onClickItem },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Icon(
            productType.icon,
            stringResource(productType.name),
            modifier = Modifier.padding(top = 4.dp)
        )

        Text(
            stringResource(productType.name),
            modifier = Modifier,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun FoodDetailDialog() {

}

@Preview(showBackground = true)
@Composable
fun FoodAddPreview() {
    MyHealthTheme {
        FoodAdd("FoodAdd", model = DiaryViewModel(), modifier = Modifier)
    }
}