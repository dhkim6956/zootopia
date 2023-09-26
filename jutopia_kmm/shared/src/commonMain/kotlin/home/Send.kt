package home

import BottomTabBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import common.TopPageBar
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource



@OptIn(ExperimentalResourceApi::class)
@Composable
fun Send(navigator: Navigator) {

    var glassesImg = "drawable/glasses.xml"
    var humanImg = "drawable/human.xml"

    val glassesIcon: Painter = painterResource(glassesImg)
    val humanIcon: Painter = painterResource(humanImg)

    var selectedTab by remember { mutableStateOf(0) }

    Column(
        verticalArrangement= Arrangement.spacedBy(15.dp)
    ) {
        TopPageBar("송금하기")
        Box(
            modifier = Modifier
                .width(300.dp)
                .height(35.dp)
                .background(deepBlue)
                .align(Alignment.CenterHorizontally),

        ) {
            Row(
                modifier = Modifier.align(Alignment.Center),
            ){
                Image(painter = glassesIcon, contentDescription = "glasses Icon")
                Spacer(modifier = Modifier.width(10.dp))
                Text("이름 검색", color = Color.White)
            }
        }

        Box(
            modifier = Modifier
                .width(300.dp)
                .height(35.dp)
                .clickable { selectedTab = 1 }
                .align(Alignment.CenterHorizontally),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(painter = humanIcon, contentDescription = "human Icon")
                Spacer(modifier = Modifier.width(10.dp)) // 이 부분 추가
                Text("이름: 김도훈 / 번호: 1")
            }
        }

        Box(
            modifier = Modifier
                .width(300.dp)
                .height(35.dp)
                .align(Alignment.CenterHorizontally),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(painter = humanIcon, contentDescription = "human Icon")
                Spacer(modifier = Modifier.width(10.dp)) // 이 부분 추가
                Text("이름: 김기홍 / 번호: 2")
            }
        }

        Box(
            modifier = Modifier
                .width(300.dp)
                .height(35.dp)
                .align(Alignment.CenterHorizontally),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(painter = humanIcon, contentDescription = "human Icon")
                Spacer(modifier = Modifier.width(10.dp)) // 이 부분 추가
                Text("이름: 김현종 / 번호: 3")
            }
        }

        Box(
            modifier = Modifier
                .width(300.dp)
                .height(35.dp)
                .align(Alignment.CenterHorizontally),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(painter = humanIcon, contentDescription = "human Icon")
                Spacer(modifier = Modifier.width(10.dp)) // 이 부분 추가
                Text("이름: 소영섭 / 번호: 4")
            }
        }

        Box(
            modifier = Modifier
                .width(300.dp)
                .height(35.dp)
                .align(Alignment.CenterHorizontally),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(painter = humanIcon, contentDescription = "human Icon")
                Spacer(modifier = Modifier.width(10.dp)) // 이 부분 추가
                Text("이름: 신성주 / 번호: 5")
            }
        }

        Box(
            modifier = Modifier
                .width(300.dp)
                .height(35.dp)
                .align(Alignment.CenterHorizontally),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(painter = humanIcon, contentDescription = "human Icon")
                Spacer(modifier = Modifier.width(10.dp)) // 이 부분 추가
                Text("이름: 임준환 / 번호: 6")
            }
        }

    }
    when (selectedTab) {
        1 -> {
            navigator.navigate("/send_detail")
        }

    }
    BottomTabBar(navigator)
}