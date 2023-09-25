@file:OptIn(ExperimentalResourceApi::class)

package home

import BottomTabBar
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import common.TopPageBar
import lease.LeasePage
import lease.LeaseScreen
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi



//@Composable
//fun Home(navigator: Navigator) {
//    Column {
//        TopPageBar("홈")
//        BottomTabBar(navigator)
//    }
//}

@Composable
fun Home(navigator: Navigator) {
    Column {
        TopPageBar("홈")
        LeasePage()
    }
    BottomTabBar(navigator)
}