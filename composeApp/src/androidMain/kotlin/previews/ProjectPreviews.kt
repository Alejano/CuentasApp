package previews

import DATA.ExpenseManager
import MODEL.Expense
import MODEL.ExpensesCategory
import PRESENTACION.ExpensesUIState
import UI.ALLExpensesHeader
import UI.ExpensesItem
import UI.ExpensesScreen
import UI.ExpensesTotalHeader
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
private fun ExpensesTotalHeaderPreview() {
    ExpensesTotalHeader(total = 1098.43)
}

@Preview(showBackground = true)
@Composable
private fun AllExpensesHeaderPreview() {
    ALLExpensesHeader()
    
}

@Preview(showBackground = true)
@Composable
private fun ExpenseItemPReview() {
    ExpensesItem(
        expense = ExpenseManager.getExpenses()[0],
        onExpenseClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun ExpenseScreemPreview() {
    ExpensesScreen(ExpensesUIState()) {}
}