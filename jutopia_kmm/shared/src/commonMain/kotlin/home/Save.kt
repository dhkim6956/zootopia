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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.svenjacobs.reveal.Reveal
import com.svenjacobs.reveal.RevealCanvasState
import com.svenjacobs.reveal.rememberRevealState
import common.TopPageBar
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

val skyBlue = Color(0xFFEBF5F7)
val deepSky = Color(0xFFC3E0E8)
val deepNavy = Color(0xFF316781)
enum class Keys { HelloWorld }
@OptIn(ExperimentalResourceApi::class)
@Composable
fun Save(navigator: Navigator, revealCanvasState: RevealCanvasState) {
    val revealState = rememberRevealState()
    var moneyImg = "drawable/money.xml"
    var checkImg = "drawable/check.xml"
    var heartImg = "drawable/heart.xml"
    var rabbit_coinImg = "drawable/rabbit_coin.xml"

    val moneyIcon: Painter = painterResource(moneyImg)
    val checkIcon: Painter = painterResource(checkImg)
    val heartIcon: Painter = painterResource(heartImg)
    val rabbit_coinIcon: Painter = painterResource(rabbit_coinImg)

    var showDialog by remember { mutableStateOf(false) }
    var money by remember { mutableStateOf("") }

    var isButtonClicked by remember { mutableStateOf(false) }

    Reveal(
        revealCanvasState = revealCanvasState,
        revealState = revealState,
    ) {

        Column {
            TopPageBar("적금정보", navigator = navigator)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(skyBlue)
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("6학년 1반", fontSize = 25.sp)
                    Row {
                        Text("양 많이요 ", fontSize = 25.sp, color = deepNavy)
                        Text("적금", fontSize = 25.sp)
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 15.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(painter = moneyIcon, contentDescription = "Money Icon")
                            Text("최고이자율", fontSize = 10.sp)
                            Text("월 10.00%", fontSize = 15.sp)
                            Text("(1개월)", fontSize = 10.sp)
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(painter = checkIcon, contentDescription = "check Icon")
                            Text("저축한도", fontSize = 10.sp)
                            Text("1천원 이상", fontSize = 15.sp)
                            Text("일주일 30만원 이내", fontSize = 15.sp)
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 15.dp)
                    ) {

                        Box(
                            modifier = Modifier

                                .width(180.dp)
                                .height(30.dp)
                                .background(deepSky)
                                .clickable {
                                    showDialog = true
                                    isButtonClicked = true
                                           },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("가입하기", color = deepNavy)

                            if (showDialog) {
                                AlertDialog(
                                    modifier = Modifier
                                        .revealable( key = Keys.HelloWorld),
                                    backgroundColor = skyBlue,
                                    onDismissRequest = { showDialog = false },
                                    text = {
                                        TextField(
                                            value = money,
                                            onValueChange = { money = it },
                                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                            placeholder = { Text("금액을 입력해주세요", fontSize = 15.sp) },
                                            colors = TextFieldDefaults.textFieldColors(
                                                backgroundColor = Color.White,
                                                focusedIndicatorColor = Color.Transparent,
                                                unfocusedIndicatorColor = Color.Transparent
                                            )
                                        )
                                        Spacer(
                                            Modifier
                                                .align(Alignment.BottomCenter)
                                                .fillMaxWidth()
                                                .height(1.dp)
                                                .background(Color.Gray)
                                        )
                                    },

                                    confirmButton = {
                                        Button(onClick = { showDialog = false }) {
                                            Text("확인")
                                        }
                                    }
                                )
                            }
                        }

                        if (isButtonClicked) {
                            LaunchedEffect(Unit) {
                                revealState.reveal(Keys.HelloWorld)
                                isButtonClicked = false  // 작업 완료 후 다시 false로 설정
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Box(
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(painter = heartIcon, contentDescription = "heart Icon")
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row {
                    Column {
                        Text("아름다운 저축을 위한 습관,", color = Color.Black)
                        Row {
                            Text("주토피아", color = deepNavy)
                            Text("와 함께 실천해요", color = Color.Black)
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Image(
                        painter = rabbit_coinIcon, contentDescription = "check Icon",
                        modifier = Modifier.width(100.dp).height(100.dp)
                    )
                }
            }
        }
    }
    BottomTabBar(navigator)
}