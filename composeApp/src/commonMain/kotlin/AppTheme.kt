import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppTheme(content: @Composable () -> Unit) {
   MaterialTheme(
       colors = MaterialTheme.colors.copy(primary = Color.Black),
       shapes = MaterialTheme.shapes.copy(
           small = AbsoluteCutCornerShape(0.dp),
           medium = AbsoluteCutCornerShape(0.dp),
           large = AbsoluteCutCornerShape(0.dp)
       )
   ) {
        content()
   }
}

@Composable
fun getColorsTheme():DarkModeColors {
    val isDartMode = false;

    val Purple = Color(0xFF6A66FF)
    val colorExpenseItem = if(isDartMode) Color(0xFF090808) else Color(0xFFF1F1F1)
    val backgroundColor = if(isDartMode) Color(0xFF1E1C1C) else Color.White
    val textColor = if(isDartMode) Color.White else Color.Black
    val AddIconColor = if(isDartMode) Purple else Color.Black
    val colorArrowRound = if(isDartMode) Purple else Color.Gray.copy(alpha = 0.2f)

    return DarkModeColors(Purple, colorExpenseItem, backgroundColor, textColor, AddIconColor,colorArrowRound)
}


data class DarkModeColors(
    val purple: Color,
    val colorExpenseItem: Color,
    val backgroundColor: Color,
    val textColor: Color,
    val addIconColor: Color,
    val colorArrowRound: Color
)