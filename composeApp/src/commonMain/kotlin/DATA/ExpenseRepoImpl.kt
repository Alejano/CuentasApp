package DATA

import DOMAIN.ExpenseRepository
import MODEL.Expense
import MODEL.ExpensesCategory

class ExpenseRepoImpl(ExpenseManager: ExpenseManager) : ExpenseRepository {

    override fun addExpense(expense: Expense) {
        ExpenseManager.addExpense(expense)
    }

    override fun editExpense(expense: Expense) {
        ExpenseManager.editExpense(expense)
    }

    override fun removeExpense(expense: Expense): List<Expense> {
        ExpenseManager.removeExpense(expense)
        return ExpenseManager.getExpenses()
    }

    override fun getExpenses(): List<Expense> {
        return ExpenseManager.getExpenses()
    }

    override fun getCategories(): List<ExpensesCategory> {
        return ExpenseManager.getCategories()
    }

    override fun getExpensesByCategory(category: ExpensesCategory): List<Expense> {
        return ExpenseManager.getExpensesByCategory(category)
    }
}