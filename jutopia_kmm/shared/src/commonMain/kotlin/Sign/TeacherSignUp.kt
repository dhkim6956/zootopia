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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.touchlab.kermit.Logger
import common.TopPageBar
import moe.tlaster.precompose.navigation.Navigator
private val log = Logger.withTag("TeacherSignUp")

@Composable
fun TeacherSignUp(navigator: Navigator, id: String?, password: String?) {
    var inviteCode by remember { mutableStateOf("") }
    var PassWord by remember { mutableStateOf("") }
    var Name by remember { mutableStateOf("") }

    var selectedTab by remember { mutableStateOf(0) }

    Column {
        TopPageBar("선생님 회원가입", navigator = navigator)
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .background(sky)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .width(220.dp)
                    .height(80.dp)
            ) {
                Text("초대코드")
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
                        value = inviteCode,
                        onValueChange = { inviteCode = it }, // 모든 글자 허용
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
            Spacer(modifier = Modifier.height(5.dp))
            Column(
                modifier = Modifier
                    .width(220.dp)
                    .height(80.dp)
            ) {
                Text("비밀번호")
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
                        value = PassWord,
                        onValueChange = { PassWord = it }, // 모든 글자 허용
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
                navigator.navigate("/signup")
            }

        }
    }
}