package NAVIGATION

import DATA.ExpenseManager
import DATA.ExpenseRepoImpl
import PRESENTACION.ExpensesViewModel
import UI.ExpensesDetailScreen
import UI.ExpensesScreen
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import getColorsTheme
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.viewmodel.viewModel

@Composable
fun Navigation(navigator: Navigator) {
    val colors = getColorsTheme()

    val viewModel = viewModel(modelClass = ExpensesViewModel::class){
        ExpensesViewModel(ExpenseRepoImpl(ExpenseManager))
    }


    NavHost(
        navigator = navigator,
        initialRoute = "/home",
        modifier = Modifier.background(color = colors.backgroundColor)
    ){
        scene(route = "/home"){
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ExpensesScreen(
                uiState,
                onExpenseClick = { expense ->
                    navigator.navigate("/addExpenses/${expense.id}")
                })
        }

        scene(route = "/addExpenses/{id}?"){
            val id = it.path<Long>("id")
            val expense = id?.let { id -> viewModel.getExpenseById(id)}
            ExpensesDetailScreen(newExpense = expense, categories = viewModel.getCategories()) { newExpense ->
                println(expense)
                if (expense == null) {
                    println("Agregado")
                    viewModel.addExpense(newExpense)
                } else {
                    println("Editado")
                    viewModel.updateExpense(newExpense)
                }
                navigator.popBackStack()
            }
        }
    }
}