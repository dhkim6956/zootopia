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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

private val log = Logger.withTag("MainPage")

class loginAPI {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation){
            json(
                Json { ignoreUnknownKeys = true }
            )
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun login(member_id: String, member_pwd: String): Boolean {
        val requestBody = mapOf(
            "member_id" to member_id,
            "member_pwd" to member_pwd,
        )

        return try {
            val response: HttpResponse = client.post("http://j9c108.p.ssafy.io:8000/member-server/api/sign-in") {
                contentType(ContentType.Application.Json)
                body = Json.encodeToString(requestBody)
            }

            // Check the response and return true for success, false for failure.
            // The condition should be modified based on your server's actual response.
            response.status.value in 200..299
        } catch (e: Exception) {
            log.e(e) { "Error during sign in" }
            false
        }
    }
}

val navy = Color(0xFF3F51B5)
val sky = Color(0xFFBDEBFF)
@OptIn(ExperimentalResourceApi::class, ExperimentalComposeUiApi::class)
@Composable
fun MainPage(navigator: Navigator) {
    var id by remember { mutableStateOf("") }
    var pwd by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }
    var schoolImg = "drawable/school.png"
    val schoolIcon: Painter = painterResource(schoolImg)
    val coroutineScope = rememberCoroutineScope()
    var loginModal by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier,
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
                    value = id,
                    onValueChange = { id = it }, // 모든 글자 허용
                    placeholder = { if (id.isEmpty()) Text("Enter your ID") },
                    textStyle = TextStyle(fontSize = 20.sp),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {keyboardController?.hide()
                        focusManager.clearFocus()
                    })
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
                    value = pwd,
                    onValueChange = { pwd = it }, // 모든 글자 허용
                    placeholder = { if (pwd.isEmpty()) Text("Enter your PassWord") },
                    textStyle = TextStyle(fontSize = 20.sp),
                    visualTransformation = PasswordVisualTransformation(), // 비밀번호 가리기
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {keyboardController?.hide()
                        focusManager.clearFocus()})
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Box(
                modifier = Modifier
                    .width(70.dp)
                    .height(38.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(navy)
                    .clickable {
                        val api = loginAPI()
                        coroutineScope.launch {
                            val success = api.login(id!!, pwd!!)
                            if (success) { // If login is successful.
                                selectedTab = 2
                            } else { // If login fails.
                                loginModal = true // Show the dialog.
                            }
                        }
                    },
                        contentAlignment = Alignment.Center
            )
            {
                Text("로그인", color = Color.White)
            }
            if (loginModal) {
                AlertDialog(
                    onDismissRequest = { loginModal = false },
                    title = { Text("로그인 실패") },
                    text = { Text("로그인에 실패하였습니다.", fontSize = 20.sp) },
                    confirmButton = {
                        Button(onClick = { loginModal = false }) {
                            Text("확인")
                        }
                    }
                )
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

            2 -> {
                navigator.navigate("/home")
            }
        }
    }
}