package DOMAIN

import MODEL.Expense
import MODEL.ExpensesCategory

interface ExpenseRepository {

    fun addExpense(expense:Expense)
    fun editExpense(expense:Expense)
    fun removeExpense(expense:Expense):List<Expense>
    fun getExpenses():List<Expense>
    fun getCategories():List<ExpensesCategory>
    fun getExpensesByCategory(category: ExpensesCategory):List<Expense>

}