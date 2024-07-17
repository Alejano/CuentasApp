package DATA

import MODEL.Expense
import MODEL.ExpensesCategory

object ExpenseManager{
    //Singleton

    private var currentID = 1L

    val fakeExpenseList= mutableListOf(
        Expense(
            id = currentID++,
            amount = 10.0,
            description = "weekly buy",
            category = ExpensesCategory.GROCERIES
        ),
        Expense(
            id = currentID++,
            amount = 550.0,
            description = "Mecanico",
            category = ExpensesCategory.CAR
        ),
        Expense(
            id = currentID++,
            amount = 240.50,
            description = "nueva cortina",
            category = ExpensesCategory.HOSE
        ),
        Expense(
            id = currentID++,
            amount = 1300.0,
            description = "Amazon buy",
            category = ExpensesCategory.OTHER
        ),
        Expense(
            id = currentID++,
            amount = 3900.0,
            description = "Sharon Boda",
            category = ExpensesCategory.PARTY
        ),
        Expense(
            id = currentID++,
            amount = 57.0,
            description = "Starbucks",
            category = ExpensesCategory.COFFEE
        ),
        Expense(
            id = currentID++,
            amount = 20.0,
            description = "Chetos",
            category = ExpensesCategory.SNACKS
        )
    )

    fun addExpense(expense: Expense){
        fakeExpenseList.add(expense.copy(id = currentID++))
    }
    fun editExpense(expense: Expense){
        val index = fakeExpenseList.indexOfFirst { it.id == expense.id }
        if(index != -1){
            fakeExpenseList[index] = fakeExpenseList[index].copy(
                amount = expense.amount,
                description = expense.description,
                category = expense.category
            )
        }
    }
    fun removeExpense(expense: Expense){
        fakeExpenseList.remove(expense)
    }
    fun getExpenses(): List<Expense>{
        return fakeExpenseList
    }
    fun getCategories(): List<ExpensesCategory>{
        return listOf(
            ExpensesCategory.GROCERIES,
            ExpensesCategory.CAR,
            ExpensesCategory.HOSE,
            ExpensesCategory.OTHER,
            ExpensesCategory.PARTY,
            ExpensesCategory.COFFEE,
            ExpensesCategory.SNACKS
        )
    }
    fun getExpensesByCategory(category: ExpensesCategory): List<Expense>{
        return fakeExpenseList.filter { it.category == category }
    }

}
