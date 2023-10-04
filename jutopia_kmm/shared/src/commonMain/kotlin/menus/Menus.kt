package menus

import BottomTabBar
import UserInfo
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import co.touchlab.kermit.Logger
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import moe.tlaster.precompose.navigation.Navigator
import pathTo

@Composable
fun Menus(navigator: Navigator) {

//    val store: KStore<UserInfo> = storeOf(filePath = pathTo("user"))
//
//    val testData = UserInfo("0e4ad1d7-6a52-499a-92f2-915c3e6f3cb", "student22", "ssafy초등학교", 6, 1, 3)
//
//    var getData by remember { mutableStateOf("데이터 없음") }
//
//    LaunchedEffect(1) {
//        store.delete()
//        store.set(testData)
//        val temp: UserInfo? = store.get()
//
//        if (temp != null) {
//            Logger.d(temp.id)
//        }
//    }

    Column {
    }
        BottomTabBar(navigator, 4)
}