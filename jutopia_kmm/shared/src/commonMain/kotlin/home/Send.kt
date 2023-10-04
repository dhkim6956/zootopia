package home

import BottomTabBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.TopPageBar
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource



@OptIn(ExperimentalResourceApi::class)
@Composable
fun Send(navigator: Navigator) {

    var name by remember { mutableStateOf("") }

    var glassesImg = "drawable/glasses.xml"
    var humanImg = "drawable/human.xml"

    val glassesIcon: Painter = painterResource(glassesImg)
    val humanIcon: Painter = painterResource(humanImg)

    var selectedTab by remember { mutableStateOf(0) }

    Column(
        verticalArrangement= Arrangement.spacedBy(15.dp)
    ) {
        TopPageBar("송금하기", navigator=navigator)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .align(Alignment.CenterHorizontally),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                TextField(
                    modifier = Modifier
                        .width(300.dp),
                    value = name,
                    onValueChange = { name = it }, // 모든 글자 허용
                    placeholder = { if (name.isEmpty()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(painter = glassesIcon, contentDescription = "glasses Icon")
                            Spacer(Modifier.width(10.dp))
                            Text("이름 검색", fontSize = 15.sp, color = Color.White)
                            }
                        }
                      },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        backgroundColor = deepBlue,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

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
                Spacer(modifier = Modifier.width(10.dp))
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
                Spacer(modifier = Modifier.width(10.dp))
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
    BottomTabBar(navigator, 0)
}