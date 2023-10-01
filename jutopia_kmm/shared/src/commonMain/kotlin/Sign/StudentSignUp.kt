package Sign

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.touchlab.kermit.Logger
import common.TopPageBar
import moe.tlaster.precompose.navigation.Navigator
private val log = Logger.withTag("StudentSignUp")

@Composable
fun StudentSignUp(navigator: Navigator, id: String?, password: String?) {
    var school by remember { mutableStateOf("") }
    var grade by remember { mutableStateOf("") }
    var studentClass by remember { mutableStateOf("") }
    var Name by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }

    log.i { " $id, $password "}

    Column {
        TopPageBar("학생 회원가입", navigator = navigator)
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .width(220.dp)
                    .height(80.dp)
            ) {
                Text("학교")
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(5.dp))
                        .border(2.dp, Color.LightGray)
                ) {
                    TextField(
                        modifier = Modifier
                            .width(300.dp),
                        value = school,
                        onValueChange = { school = it }, // 모든 글자 허용
                        textStyle = TextStyle(fontSize = 20.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                ) {
                    Text("학년")
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(5.dp))
                            .border(2.dp, Color.LightGray)
                    ) {
                        TextField(
                            modifier = Modifier
                                .width(300.dp),
                            value = grade,
                            onValueChange = { if (it.all { char -> char.isDigit() }) grade = it }, // 숫자만 허용
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // 숫자 키보드 사용
                            textStyle = TextStyle(fontSize = 20.sp),
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                backgroundColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.width(30.dp))
                Column(
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                ) {
                    Text("반")
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(5.dp))
                            .border(2.dp, Color.LightGray)
                    ) {
                        TextField(
                            modifier = Modifier
                                .width(300.dp),
                            value = studentClass,
                            onValueChange = { if (it.all { char -> char.isDigit() }) studentClass = it }, // 숫자만 허용
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // 숫자 키보드 사용
                            textStyle = TextStyle(fontSize = 20.sp),
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                backgroundColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(5.dp))
            Column(
                modifier = Modifier
                    .width(220.dp)
                    .height(80.dp)
            ) {
                Text("이름")
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(5.dp))
                        .border(2.dp, Color.LightGray)
                ) {
                    TextField(
                        modifier = Modifier
                            .width(300.dp),
                        value = Name,
                        onValueChange = { Name = it }, // 모든 글자 허용
                        textStyle = TextStyle(fontSize = 20.sp),
                        visualTransformation = PasswordVisualTransformation(), // 비밀번호 가리기
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }


            Column(
                modifier = Modifier
                    .width(220.dp)
                    .height(80.dp)
            ) {
                Text("번호")
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(5.dp))
                        .border(2.dp, Color.LightGray)
                ) {
                    TextField(
                        modifier = Modifier
                            .width(300.dp),
                        value = number,
                        onValueChange = { number = it }, // 모든 글자 허용
                        textStyle = TextStyle(fontSize = 20.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Spacer(modifier = Modifier.width(10.dp))

            Box(
                modifier = Modifier
                    .width(70.dp)
                    .height(38.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .border(2.dp, Color.LightGray)
                    .clickable { selectedTab = 1 },
                contentAlignment = Alignment.Center
            )
            {
                Text("회원가입", color = navy)
            }
        }

        when (selectedTab) {
            1 -> {
                navigator.navigate("/home")
            }

        }
    }
}