package UI

import DATA.TitleTopBarTypes
import MODEL.Expense
import MODEL.ExpensesCategory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import getColorsTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpensesDetailScreen(
    newExpense: Expense? = null,
    categories: List<ExpensesCategory> = emptyList(),
    addExpenseAndNavidateBack:(Expense) -> Unit = {}
) {

    val colors = getColorsTheme()
    var price by remember { mutableStateOf(newExpense?.amount ?: 0.0) }
    var description by remember { mutableStateOf(newExpense?.description ?: "") }
    var expensesCategory by remember { mutableStateOf(newExpense?.category?.name ?: "") }
    var categorySelected by remember { mutableStateOf(newExpense?.category?.name ?: "Select a category") }
    var sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    var keyboardController = LocalSoftwareKeyboardController.current
    var scope = rememberCoroutineScope()
    


    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            CategoryBottomSheetContent(categories = categories,){

                expensesCategory = it.name
                categorySelected = it.name
                scope.launch {
                    sheetState.hide()
                }
            }
        }
    ){
        Column(
            modifier = Modifier.fillMaxSize().padding(vertical = 16.dp, horizontal = 8.dp)
        ) {
            ExpanseAmount(
                priceContent = price,
                onPriceChange = {
                    price = it
                },
                keyboardController = keyboardController
            )
            Spacer(modifier = Modifier.height(16.dp))
            ExpenseTypeSelector(
                categorySelected = categorySelected,
                openBottomSheet = {
                    scope.launch {
                        sheetState.show()
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            EspansesDescription(
                descriptionContent = description,
                onDescriptionChange = {
                    description = it
                },
                keyboardController = keyboardController
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(45)),
                onClick = {
                    val thisexpense = Expense(
                        amount = price,
                        description = description,
                        category =  ExpensesCategory.valueOf(expensesCategory)
                        )
                    val expenseFromEdit = newExpense?.id?.let { thisexpense.copy(id = it) }
                    addExpenseAndNavidateBack(expenseFromEdit ?: thisexpense)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colors.purple,
                    contentColor = Color.White
                ),
                enabled = price != 0.0 && expensesCategory.isNotBlank() && description.isNotBlank()
            ){
                newExpense?.let {
                    Text(text = TitleTopBarTypes.EDIT.value, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                    return@Button
                }
                Text(text = TitleTopBarTypes.ADD.value, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }


}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ExpanseAmount(
    priceContent:Double,
    onPriceChange:(Double) -> Unit,
    keyboardController: SoftwareKeyboardController?
) {
    val colors = getColorsTheme()
    var text by remember { mutableStateOf(priceContent.toString()) }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Amount", fontSize = 20.sp, color = Color.Gray,fontWeight = FontWeight.SemiBold)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text,fontSize = 25.sp, fontWeight = FontWeight.ExtraBold,color = colors.textColor)

            TextField(
                value = text,
                onValueChange = { newtext:String ->
                    val numericText = newtext.filter {it.isDigit() || it == '.'}
                    text = if(numericText.isNotEmpty() && numericText.count {it == '.'}<=1){
                        try{
                            val newValue = numericText.toDouble()
                            onPriceChange(newValue)
                            numericText
                        }catch (e:NumberFormatException){
                            ""
                        }
                    }else{
                        onPriceChange(0.0)
                        ""
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colors.textColor,
                    backgroundColor = colors.backgroundColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent,
                ),
                textStyle = TextStyle(fontSize = 35.sp, fontWeight = FontWeight.ExtraBold)
            )
            Text(text = "MX", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold,color = Color.Gray)
        }
        Divider(color = Color.Black,thickness = 2.dp)
    }
}

@Composable
private fun CategoryBottomSheetContent(
    categories: List<ExpensesCategory>,
    onCategorySelected: (ExpensesCategory) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(16.dp),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center
    ){
        items(categories){category->
            CategoryItem(category, onCategorySelected)
        }
    }
}

@Composable
private fun CategoryItem(
    category: ExpensesCategory,
    onCategorySelected: (ExpensesCategory) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable {
                 onCategorySelected(category)
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(48.dp).clip(CircleShape),
            imageVector = category.icon,
            contentDescription = "Category icon",
            contentScale = ContentScale.Crop
        )
        Text(category.name)
    }
}


@Composable
fun ExpenseTypeSelector(
    categorySelected:String,
    openBottomSheet:() -> Unit
) {
    val colors = getColorsTheme()

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ){
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
        ){
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "Expenses made for",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )
            Text(
                text = categorySelected,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = colors.textColor
            )
        }
        IconButton(
            modifier = Modifier.clip(RoundedCornerShape(35)).background(colors.colorArrowRound),
            onClick = {
                openBottomSheet.invoke()
            }
        ){
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Select category",
                tint = colors.textColor
            )
        }
    }
}


@Composable
fun EspansesDescription(
    descriptionContent:String,
    onDescriptionChange:(String) -> Unit,
    keyboardController: SoftwareKeyboardController?
) {
    var text by remember { mutableStateOf(descriptionContent)}
    var colors= getColorsTheme()

    Column{
        Text(text = "Description", fontSize = 20.sp, color = Color.Gray,fontWeight = FontWeight.SemiBold)
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = {
                if(it.length <= 200){
                    text = it
                    onDescriptionChange(it)
                }
            },
            singleLine = false,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colors.textColor,
                backgroundColor = colors.backgroundColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedLabelColor = Color.Transparent,
            ),
            textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )
        )
        Divider(color = Color.Black,thickness = 2.dp)
    }
}