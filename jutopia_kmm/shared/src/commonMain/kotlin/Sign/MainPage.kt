package Sign

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.touchlab.kermit.Logger
import common.TopPageBar
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

private val log = Logger.withTag("MainPage")

val navy = Color(0xFF3F51B5)
val sky = Color(0xFFBDEBFF)
@OptIn(ExperimentalResourceApi::class)
@Composable
fun MainPage(navigator: Navigator) {
    var ID by remember { mutableStateOf("") }
    var PassWord by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }
    var schoolImg = "drawable/school.png"

    val schoolIcon: Painter = painterResource(schoolImg)

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(sky)
        ) {
            Image(painter = schoolIcon, contentDescription = "School Icon",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .size(250.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Column (
            modifier = Modifier
                .width(220.dp)
                .height(80.dp)
        ) {
            Text("ID")
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
                    value = ID,
                    onValueChange = { ID = it }, // 모든 글자 허용
                    placeholder = { if (ID.isEmpty()) Text("Enter your ID") },
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
                    placeholder = { if (PassWord.isEmpty()) Text("Enter your PassWord") },
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
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Box(
                modifier = Modifier
                    .width(70.dp)
                    .height(38.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(navy),
                contentAlignment = Alignment.Center
            ) {
                Text("로그인", color = Color.White)
            }
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