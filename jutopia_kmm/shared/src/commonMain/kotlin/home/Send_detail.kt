package home

import BottomTabBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.sp
import common.TopPageBar
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

val Gray = Color(0xFFB1B6B9)
@OptIn(ExperimentalResourceApi::class)
@Composable
fun Send_detail(navigator: Navigator) {
    var checkHuman = "drawable/checkHuman.xml"

    val checkHumanIcon: Painter = painterResource(checkHuman)

    Column {
        TopPageBar("송금하기")
        Box {
            Column {
                Text("나의 계좌에서... 에서")
                Text("잔액 260,000 ₩", fontSize = 15.sp, color = Gray)
            }
        }

        Box {
            Image(painter = checkHumanIcon, contentDescription = "checkHuman Icon")
        }
    }
    BottomTabBar(navigator)
}