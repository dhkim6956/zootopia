package menus

import BottomTabBar
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

    val store: KStore<UserInfo> = storeOf(filePath = pathTo("user"))

    Logger.d(pathTo("user"))

    val testData = UserInfo(uuid = "testuuid", id = "testid")

    var getData by remember { mutableStateOf("데이터 없음") }

    LaunchedEffect(1) {
//        store.set(testData)
        val temp: UserInfo? = store.get()

        if (temp != null) {
            Logger.d(temp.id)
            getData = temp.id
        }
    }

    Text(getData)

    Column {
    }
        BottomTabBar(navigator, 4)
}