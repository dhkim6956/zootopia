package home

import BottomTabBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.TopPageBar
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

val Gray = Color(0xFFB1B6B9)
val Navy = Color(0xFF3F51B5)
@OptIn(ExperimentalResourceApi::class)
@Composable
fun Send_detail(navigator: Navigator) {
    var money by remember { mutableStateOf("") }
    var checkHuman = "drawable/checkHuman.xml"

    val checkHumanIcon: Painter = painterResource(checkHuman)

    Column {
        TopPageBar("송금하기", navigator=navigator)
        Column(
            modifier = Modifier
                .padding(start = 30.dp)
        ) {
            Box {
                Column {
                    Text("나의 계좌에서... 에서")
                    Text("잔액 260,000 ₩", fontSize = 15.sp, color = Gray)
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row {
                Image(
                    painter = checkHumanIcon,
                    contentDescription = "checkHuman Icon",
                    modifier = Modifier.offset(y = 10.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text("김도훈님에게")
                    Text("번호: 1", color = Gray)
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier.fillMaxHeight(),
                value = money,
                onValueChange = { if (it.all { char -> char.isDigit() }) money = it }, // 숫자만 허용
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // 숫자 키보드 사용
                shape = RoundedCornerShape(16.dp),
                textStyle= TextStyle(fontSize=25.sp),
                placeholder = { Text("얼마를 보낼까요?", fontSize = 20.sp) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Box(
                modifier = Modifier
                    .width(50.dp)
                    .height(35.dp)
                    .background(color = Navy),
                contentAlignment = Alignment.Center
            ) {
                Text("송금", color = Color.White)
            }
        }
    }
    BottomTabBar(navigator)
}