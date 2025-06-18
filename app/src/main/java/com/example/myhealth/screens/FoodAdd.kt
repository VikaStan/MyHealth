package com.example.myhealth.screens

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myhealth.R
import com.example.myhealth.data.datasource.local.MealDao
import com.example.myhealth.data.datasource.local.MealDayStat
import com.example.myhealth.data.datasource.local.entity.MealEntity
import com.example.myhealth.data.repository.DiaryRepository
import com.example.myhealth.domain.models.Product
import com.example.myhealth.domain.models.ProductType
import com.example.myhealth.models.FoodAddViewModel
import com.example.myhealth.presentation.diary.DiaryScreenViewModel
import com.example.myhealth.presentation.diary.MealType
import com.example.myhealth.ui.theme.MyHealthTheme
import com.example.myhealth.utility.parseFloat
import com.example.myhealth.utility.parseInt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun FoodAdd(
    eatingType: String?,
    modifier: Modifier,
    modelDiary: DiaryScreenViewModel = hiltViewModel(),
    model: FoodAddViewModel = hiltViewModel()
) {

    val selectedProductType by model.selectedTypeProductOld.collectAsState()
    val eatingFoodTime by model.eatingFoodTime.collectAsState()

    model.getEatingTimeName(eatingType)
    model.getEatingFoodTime(modelDiary, eatingType)

    if (model.foodAddDialog) {
        FoodDetailDialog(
            showDialog = model::foodAddDialogShow,
            selectedProductType,
            model::addProduct,
            model::editProduct
        )
    }
    if (model.foodEditDialog) {
        FoodDetailDialog(
            showDialog = model::foodEditDialogShow,
            selectedProductType,
            model::addProduct,
            model::editProduct,
            true
        )
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val context = LocalContext.current
        var showTimePicker by remember { mutableStateOf(false) }
        var mealTime by remember { mutableStateOf(java.time.LocalTime.now()) }

        if (showTimePicker) {
            android.app.TimePickerDialog(
                context,
                { _, hour, minute ->
                    mealTime = java.time.LocalTime.of(hour, minute)
                    showTimePicker = false
                },
                mealTime.hour,
                mealTime.minute,
                true
            ).show()
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(model.eatingTimeName),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = mealTime.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")),
                modifier = Modifier
                    .border(1.dp, Color.Black, RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .clickable { showTimePicker = true }
            )
        }

        FoodSection(Modifier, model::onProductItemSelected, eatingFoodTime.productList)

        FoodDetailList(model.productOlds, model::onEditSwipe, model::onDelSwipe)
    }
}

@Composable
fun FoodSection(
    modifier: Modifier = Modifier,
    onProductItemSelected: (Product) -> Unit,
    productOlds: MutableList<Product>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3), modifier = modifier
            .padding(8.dp)
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer.copy(
                    alpha = .50f
                ), shape = RoundedCornerShape(8.dp)
            ), horizontalArrangement = Arrangement.Center
    ) {
        listOf(
            ProductType.Eggs,
            ProductType.Soup,
            ProductType.Fish,
            ProductType.Meat,
            ProductType.Bakery,
            ProductType.Candies,
            ProductType.Cheese,
            ProductType.Fruts,
            ProductType.Porridge,
            ProductType.Snack,
            ProductType.Vegetables,
            ProductType.Water,
            ProductType.OtherFood,
        ).forEach { productList ->
            item {

                Box(contentAlignment = Alignment.TopEnd) {
                    val count = productOlds.count { it.productCategory == productList }
                    FoodSectionItem(productList, onProductItemSelected, count)
                    if (count != 0) {
                        Text(
                            count.toString(),
                            style = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .size(27.dp)
                                .padding(top = 6.dp, end = 6.dp)
                                .border(1.dp, color = Color.Black, CircleShape)
                                .clip(CircleShape)
                                .background(
                                    color = MaterialTheme.colorScheme.secondaryContainer.copy(
                                        0.5f
                                    )
                                )
                        )
                    }
                }

            }

        }
    }
}

@Composable
fun FoodSectionItem(
    productType: ProductType,
    onProductItemSelected: (Product) -> Unit,
    count: Int
) {
    val color = if (count > 0) Color.Green.copy(alpha = 0.8f) else Color.Transparent
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(2.dp, color = Color.Black, RoundedCornerShape(8.dp))
            .background(
                color = color, shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .clickable { onProductItemSelected(Product(0, "", 0, 0, 0, "", productType)) },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Icon(
            productType.icon,
            stringResource(productType.name),
            modifier = Modifier.padding(top = 4.dp)
        )

        Text(
            stringResource(productType.name), modifier = Modifier, textAlign = TextAlign.Center
        )
    }
}

@Composable
fun FoodDetailList(
    productOlds: SnapshotStateList<Product>,
    onEditSwipe: (Product) -> Unit,
    onDelSwipe: (Product) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer.copy(
                    alpha = .50f
                ), shape = RoundedCornerShape(8.dp)
            )
    ) {
        items(productOlds) {

            val delete = SwipeAction(onSwipe = {
                onDelSwipe(it)
            }, icon = {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete product",
                    modifier = Modifier.padding(16.dp),
                    tint = Color.White
                )
            }, background = Color.Red.copy(alpha = 0.5f), isUndo = true
            )
            val archive = SwipeAction(onSwipe = {
                onEditSwipe(it)
            }, icon = {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "edit product",
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
fun FoodDetailListItem(productOld: Product) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer.copy(
                    alpha = .50f
                ), shape = RoundedCornerShape(8.dp)
            ), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(/*verticalAlignment = Alignment.,*/
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(productOld.productCategory.icon, stringResource(productOld.productCategory.name))
            Text(
                stringResource(productOld.productCategory.name),
                Modifier.padding(end = 4.dp),
                style = MaterialTheme.typography.labelLarge,

                )
            Text(
                "${(productOld.gramms.toFloat() / 100) * productOld.caloriesPer100Gramm} калл. в ${productOld.gramms} гр",
                style = MaterialTheme.typography.labelLarge
            )
        }
        OutlinedTextField(productOld.description,
            { productOld.description = it },
            Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, bottom = 8.dp, end = 8.dp),
            enabled = false,
            label = { Text(stringResource(R.string.description)) })
    }
}

@Composable
fun FoodDetailDialog(
    showDialog: (Boolean) -> Unit,
    productOld: Product,
    onAcceptProduct: (Product) -> Unit,
    editProduct: (Product) -> Unit,
    isEdit: Boolean = false
) {

    var caloriesPer100Gramm = remember { mutableIntStateOf(productOld.caloriesPer100Gramm) }
    var caloriesSummery = remember { mutableFloatStateOf(productOld.caloriesSummery) }
    var gramms = remember { mutableIntStateOf(productOld.gramms) }
    var description = remember { mutableStateOf(productOld.description) }
    val toastShow = remember { mutableStateOf(false) }
    Dialog(
        onDismissRequest = { showDialog(false) }, DialogProperties(
            dismissOnBackPress = false, dismissOnClickOutside = true
        )
    ) {
        if (toastShow.value) {
            Toast.makeText(LocalContext.current, R.string.toast_add, Toast.LENGTH_LONG).show()
            toastShow.value = false
        }
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(productOld.productCategory.icon, "")
                Text(stringResource(productOld.productCategory.name))


                OutlinedTextField(
                    caloriesPer100Gramm.intValue.toString(),
                    {
                        if (it != "") {

                            caloriesPer100Gramm.intValue = it.parseInt()
                            caloriesSummery.floatValue =
                                ((caloriesPer100Gramm.intValue.toFloat() / 100 * gramms.intValue))
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    leadingIcon = { Icon(Icons.AutoMirrored.Filled.MenuBook, "") },
                    label = { Text(stringResource(R.string.calories_100_gr)) }) // калорий на 100 гр

                OutlinedTextField(gramms.intValue.toString(),
                    {
                        if (it != "") {
                            gramms.intValue = it.parseInt()
                            caloriesSummery.floatValue =
                                ((caloriesPer100Gramm.intValue.toFloat() / 100 * gramms.intValue))
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    leadingIcon = { Icon(Icons.Default.Scale, "") },
                    label = { Text(stringResource(R.string.gramm)) }) //грамм

                OutlinedTextField(caloriesSummery.floatValue.toString(),
                    { caloriesSummery.floatValue = it.parseFloat() },
                    enabled = false,
                    leadingIcon = { Icon(Icons.Default.LocalDining, "") },
                    label = { Text(stringResource(R.string.calories_summary)) }) //общие калории


                OutlinedTextField(description.value,
                    { description.value = it },
                    minLines = 2,
                    maxLines = 3,
                    label = { Text(stringResource(R.string.description)) }) //описание


                Button({
                    if (caloriesPer100Gramm.intValue != 0 && gramms.intValue != 0 && !isEdit) onAcceptProduct(
                        Product(
                            id = productOld.id,
                            name = productOld.name,
                            caloriesPer100Gramm = caloriesPer100Gramm.intValue,
                            gramms = gramms.intValue,
                            caloriesPerGramm = productOld.caloriesPerGramm,
                            description = description.value
                        )
                    )
                    else if (caloriesPer100Gramm.intValue != 0 && gramms.intValue != 0 && isEdit) {
                        editProduct(
                            Product(
                                id = productOld.id,
                                name = productOld.name,
                                caloriesPer100Gramm = caloriesPer100Gramm.intValue,
                                gramms = gramms.intValue,
                                caloriesPerGramm = productOld.caloriesPerGramm,
                                description = description.value
                            )
                        )
                    } else toastShow.value = true
                }, Modifier) {
                    Text(stringResource(R.string.add_product_btn))
                }

                Button({ showDialog(false) }, Modifier) {
                    Text(stringResource(R.string.cancel_btn))
                }

            }
        }
    }

}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun FoodAddPreview() {
    MyHealthTheme {
        val repo = DiaryRepository(FakeMealDao())
        FoodAdd(
            MealType.BREAKFAST.value,
            modelDiary = DiaryScreenViewModel(repo),
            modifier = Modifier
        )
    }
}

private class FakeMealDao : MealDao {
    override suspend fun insert(meal: MealEntity) {}
    override fun getMealsForToday(): Flow<List<MealEntity>> = flowOf(emptyList())
    override fun getLast7DaysCalories(from: Long): Flow<List<MealDayStat>> = flowOf(emptyList())
    override fun getTodayCalories(): Flow<Int?> = flowOf(0)
    override fun getTodayProteins(): Flow<Int?> = flowOf(0)
    override fun getTodayFats(): Flow<Int?> = flowOf(0)
    override fun getTodayCarbs(): Flow<Int?> = flowOf(0)
}