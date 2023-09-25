@file:OptIn(ExperimentalResourceApi::class)

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun BottomTabBar(navigator: Navigator) {

    Box (
        modifier = Modifier
            .fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .background(color = Variables.ColorsPrimary)
                .padding(0.dp)
                .fillMaxWidth()
                .height(72.dp)
                .align(Alignment.BottomCenter)
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                BottomTabItem({ navigator.navigate("/home") }, imgStr = "drawable/house.xml", tabName = "홈")
            }
            Box(
                modifier = Modifier.weight(1f)
            ) {
                BottomTabItem(
                    { navigator.navigate(
                        "/asset",
                        NavOptions(
                            launchSingleTop = true,
                        )
                    ) },
                    imgStr = "drawable/creditcard.xml",
                    tabName = "자산"
                )
            }
            Box(
                modifier = Modifier.weight(1f)
            ) {
                BottomTabItem(
                    { navigator.navigate(
                        "/school",
                        NavOptions(
                            launchSingleTop = true,
                        )
                    ) },
                    imgStr = "drawable/graduationcap.xml",
                    tabName = "학교"
                )
            }
            Box(
                modifier = Modifier.weight(1f)
            ) {
                BottomTabItem(
                    { navigator.navigate(
                        "/news",
                        NavOptions(
                            launchSingleTop = true,
                        )
                    ) },
                    imgStr = "drawable/newspaper.xml",
                    tabName = "뉴스"
                )
            }
            Box(
                modifier = Modifier.weight(1f)
            ) {
                BottomTabItem(
                    { navigator.navigate(
                        "/menus",
                        NavOptions(
                            launchSingleTop = true,
                        )
                    ) },
                    imgStr = "drawable/list_bullet.xml",
                    tabName = "메뉴"
                )
            }
        }
    }
}

@Composable
fun BottomTabItem(navigateRoot: ()-> Unit , imgStr: String, tabName: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 56.dp)
            .padding(start = 24.dp, top = 8.dp, end = 24.dp, bottom = 8.dp)
            .clickable { navigateRoot() }
    ) {
        Image(
            painterResource(imgStr),
            null,
            modifier = Modifier.height(24.dp)
        )
        Spacer(modifier = Modifier.height(1.dp))
        Text(tabName, style = TextStyle(
            fontSize = 12.sp,
            color = Variables.ColorsOnPrimary,
        )
        )
    }
}