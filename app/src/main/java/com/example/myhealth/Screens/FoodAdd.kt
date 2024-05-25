package com.example.myhealth.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myhealth.R
import com.example.myhealth.data.Food
import com.example.myhealth.data.FoodTimeType
import com.example.myhealth.data.Product
import com.example.myhealth.data.ProductType
import com.example.myhealth.models.DiaryViewModel
import com.example.myhealth.ui.theme.MyHealthTheme
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun FoodAdd(eatingType: String?, modifier: Modifier, model: DiaryViewModel = viewModel()) {

    val selectedDay by model.selectedDay.collectAsState()

    var eatingName: Int = 0
    var eatingFoodType: Food = Food(emptyList<Product>().toMutableList(), FoodTimeType.Dinner)
    when (eatingType) {
        FoodTimeType.Lunch.n -> {
            eatingName = R.string.lunch_title
            eatingFoodType = selectedDay.lunch
        }

        FoodTimeType.Breakfast.n -> {
            eatingName = R.string.breakfast_title
            eatingFoodType = selectedDay.breakfast
        }

        FoodTimeType.Dinner.n -> {
            eatingName = R.string.dinner_title
            eatingFoodType = selectedDay.dinner
        }
    }
    Column {
        //TODO выбор времени
        Text(
            text = "${stringResource(eatingName)}!", modifier = Modifier
        )
        FoodSection({}, eatingFoodType)
        FoodDetailDialog()
        //TODO список выбранных продуктов
        FoodDetailList(eatingFoodType)
    }
}

@Composable
fun FoodSection(
    onClickItem: () -> Unit,
    currEatingFoodType: Food
) { //TODO подсвечивать выбранную категорию
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(8.dp)
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer.copy(
                    alpha = .50f
                ), shape = RoundedCornerShape(8.dp)
            ),
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
        ).forEach { productList ->
            item {

                Box(contentAlignment = Alignment.TopEnd) {
                    val count =
                        currEatingFoodType.products.count { it.productCategory == productList }
                    FoodSectionItem(
                        productList, onClickItem = onClickItem,
                        count
                    )
                    if (count != 0) {
                        Text(
                            count.toString(),
                            style = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.size(27.dp).padding(top = 6.dp, end = 6.dp)
                                .border(1.dp, color = Color.Black, CircleShape).clip(CircleShape)
                                .background(
                                    color = MaterialTheme.colorScheme.secondaryContainer.copy(
                                        0.5f
                                    )
                                )
                        )
                    }
                    /*Icon(Icons.Default.Cancel,
                        "",modifier = Modifier.padding(top = 6.dp, end = 8.dp).clip(
                        CircleShape
                    )
                       .clickable { })*/
                }

            }

        }
    }
}

@Composable
fun FoodSectionItem(productType: ProductType, onClickItem: () -> Unit, count: Int) {
    val color = if (count > 0) Color.Green.copy(alpha = 0.8f) else Color.Transparent
    Column(
        modifier = Modifier
            .fillMaxWidth().padding(4.dp)
            .border(2.dp, color = Color.Black, RoundedCornerShape(8.dp))
            .background(
                color = color,
                shape = RoundedCornerShape(8.dp)
            )
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
fun FoodDetailList(selFoodTime: Food) {

    LazyColumn(
        modifier = Modifier.padding(8.dp).fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer.copy(
                    alpha = .50f
                ), shape = RoundedCornerShape(8.dp)
            )
    ) {
        items(selFoodTime.products) {
            val delete = SwipeAction(
                onSwipe = {

                },
                icon = {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete chat",
                        modifier = Modifier.padding(16.dp),
                        tint = Color.White
                    )
                }, background = Color.Red.copy(alpha = 0.5f),
                isUndo = true
            )
            val archive = SwipeAction(
                onSwipe = {},
                icon = {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "archive chat",
                        modifier = Modifier.padding(16.dp),

                        tint = Color.White

                    )
                }, background = Color(0xFF50B384).copy(alpha = 0.7f)
            )
            SwipeableActionsBox(
                modifier = Modifier,
                swipeThreshold = 200.dp,
                startActions = listOf(archive),
                endActions = listOf(delete)
            ) {
                FoodDetailListItem(it)
            }
        }
    }
}

@Composable
fun FoodDetailListItem(product: Product) {
    Row(
        modifier = Modifier.padding(8.dp).fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer.copy(
                    alpha = .50f
                ), shape = RoundedCornerShape(8.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(product.productCategory.icon, stringResource(product.productCategory.name))
            Text(stringResource(product.productCategory.name))
        }

        Text("${(product.gramms.toFloat() / 100) * product.caloriesPer100Gramms} калл. в ${product.gramms} гр")

        Text(product.description, textAlign = TextAlign.Center, fontStyle = FontStyle.Italic)

        /* Column {
             Icon(Icons.Default.Edit, "edit")
             Icon(Icons.Default.Delete, "delete")
         }*/


    }
}

@Composable
fun FoodDetailDialog() {

}

@Preview(showBackground = true)
@Composable
fun FoodAddPreview() {
    MyHealthTheme {
        FoodAdd(FoodTimeType.Breakfast.n, model = DiaryViewModel(), modifier = Modifier)
    }
}