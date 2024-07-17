
import DATA.TitleTopBarTypes
import NAVIGATION.Navigation
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Apps
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator


@Composable
fun App() {
    PreComposeApp{
        val colors = getColorsTheme()
        val navigator = rememberNavigator()
        val titleTopBar = getTitleTopAppBar(navigator)
        val isEditorAddExpenses = titleTopBar != TitleTopBarTypes.DASHBOARD.value
        AppTheme {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        elevation = 1.dp,
                        backgroundColor = colors.backgroundColor,
                        title = {
                            Text(text = titleTopBar,fontSize = 20.sp,color = colors.textColor,)
                        },
                        navigationIcon = {
                            if(isEditorAddExpenses){
                                IconButton(
                                    onClick = {
                                        navigator.popBackStack()
                                    }
                                ){
                                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "backArrorIcon", tint = colors.textColor)
                                }
                            }else{
                                IconButton(
                                    onClick = {}
                                ){
                                    Icon(imageVector = Icons.Default.Apps, contentDescription = "DashboardIcon", tint = colors.textColor)
                                }
                            }

                        }
                    )
                },
                floatingActionButton = {
                    if(!isEditorAddExpenses){
                        FloatingActionButton(
                            modifier = Modifier.padding(16.dp),
                            onClick = {
                                      navigator.navigate("/addExpenses")
                            },
                            shape = RoundedCornerShape(50),
                            backgroundColor = colors.addIconColor,
                            contentColor = Color.White
                        ){
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "AddIcon",
                                tint = Color.White
                            )
                        }
                    }
                }
            ) {
                Navigation(navigator)
            }
        }
    }
}

@Composable
fun getTitleTopAppBar(navigator: Navigator):String {
    var titleTopBar = TitleTopBarTypes.DASHBOARD

    val isOnAddExpenses = navigator.currentEntry.collectAsState(null).value?.route?.route.equals("/addExpenses/{id}?")
    if (isOnAddExpenses){
        titleTopBar = TitleTopBarTypes.ADD
    }

    val isOnEditExpenses = navigator.currentEntry.collectAsState(null).value?.path<Long>("id")
    if (isOnEditExpenses != null){
        titleTopBar = TitleTopBarTypes.EDIT
    }
    return titleTopBar.value
}
