package PRESENTACION

import DOMAIN.ExpenseRepository
import MODEL.Expense
import MODEL.ExpensesCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

data class ExpensesUIState(
    val expenses: List<Expense> = emptyList(),
    val total: Double = 0.0
)
class ExpensesViewModel(private val repo: ExpenseRepository) : ViewModel(){

    private val _uiState = MutableStateFlow(ExpensesUIState())
    val uiState = _uiState.asStateFlow()
    private val allExpenses = repo.getExpenses()

    init {
        getAllExpenses()
    }

    private fun updateUIState(){
        _uiState.update {state->
            state.copy(expenses = allExpenses,total = allExpenses.sumOf { it.amount })
        }
    }
    private fun getAllExpenses(){
        viewModelScope.launch {
            updateUIState()
        }
    }

    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            repo.addExpense(expense)
            updateUIState()
        }
    }
    fun updateExpense(expense: Expense) {
        viewModelScope.launch {
            repo.editExpense(expense)
            updateUIState()
        }
    }

    fun deletExpense(expense: Expense) {
        viewModelScope.launch {
            repo.removeExpense(expense)
            updateUIState()
        }
    }

    fun getExpenseById(id: Long): Expense {
        return allExpenses.first { it.id == id }
    }

    fun getCategories(): List<ExpensesCategory> {
        return repo.getCategories()
    }

}