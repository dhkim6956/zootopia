package com.ssafy.jutopia.android

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ssafy.jutopia.android.ui.BankPage
import com.ssafy.jutopia.android.ui.LeasePage
import com.ssafy.jutopia.android.ui.MainPage
import com.ssafy.jutopia.android.ui.MarketPage
import com.ssafy.jutopia.android.ui.NoticePage
import com.ssafy.jutopia.android.ui.StockPage
import com.ssafy.jutopia.android.ui.TradePage
import com.ssafy.jutopia.android.ui.theme.MyApplicationTheme

enum class Feature(val title: String) {
    Main("주토피아"), Bank("은행"), Lease("임대"), Market("마켓"), Notice("공지"), Stock("주식"), Trade("거래"),
    Num1("기능1"), Num2("기능2"), Num3("기능3"),
    History("거래내역"), Export("송금")
}


@Composable
fun JutopiaApp(
    modifier: Modifier = Modifier,
//    viewModel = null,
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Feature.valueOf(
        backStackEntry?.destination?.route ?: Feature.Main.name
    )

    val featureOptions = listOf(
        Pair(R.drawable.bank, Feature.Bank),
        Pair(R.drawable.lease, Feature.Lease),
        Pair(R.drawable.market, Feature.Market),
        Pair(R.drawable.notice, Feature.Notice),
        Pair(R.drawable.stock, Feature.Stock),
        Pair(R.drawable.trade, Feature.Trade),
    )
    val pointOptions = listOf(
        Feature.History, Feature.Export
    )

    Scaffold(
        topBar = {
            CustomTopBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )

        },
        bottomBar = {
            CustomBottomNavigation(currentScreen = currentScreen, navController = navController)
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Feature.Main.name,
            modifier = modifier.padding(it)
        ) {
            composable(Feature.Main.name) {
                MainPage(featureOptions, navController)
            }
            composable(Feature.Bank.name) {
                BankPage()
            }
            composable(Feature.Lease.name) {
                LeasePage()
            }
            composable(Feature.Market.name) {
                MarketPage()
            }
            composable(Feature.Notice.name) {
                NoticePage()
            }
            composable(Feature.Stock.name) {
                StockPage()
            }
            composable(Feature.Trade.name) {
                TradePage()
            }
            composable(Feature.Num1.name) {
                Text("기능1")
            }
            composable(Feature.Num2.name) {
                Text("기능2")
            }
            composable(Feature.Num3.name) {
                Text("기능3")
            }
            composable(Feature.History.name) {
                Text("거래내역")
            }
            composable(Feature.Export.name) {
                Text("송금")
            }
        }
    }
}

@Composable
fun CustomTopBar(
    currentScreen: Feature,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(currentScreen.title) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "뒤로가기"
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Filled.Face,
                    contentDescription = "주토피아 로고로 변경 예정"
                )
            }
        }
    )
}

@Composable
fun CustomBottomNavigation(
    currentScreen: Feature,
    navController: NavHostController
) {
    BottomNavigation {
        val items = listOf(
            Feature.Main,
            Feature.Num1,
            Feature.Num2,
            Feature.Num3
        )
        items.forEach { feature ->
            BottomNavigationItem(
                icon = { Icon(imageVectorForFeature(feature), contentDescription = null) },
                label = { Text(feature.title) },
                selected = currentScreen == feature,
                onClick = {
                    navController.navigate(feature.name) {
                        popUpTo(feature.name) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun imageVectorForFeature(feature: Feature): ImageVector {
    return when (feature) {
        Feature.Main -> Icons.Default.Home
        Feature.Num1 -> Icons.Default.Favorite
        Feature.Num2 -> Icons.Default.Settings
        Feature.Num3 -> Icons.Default.Person
        else -> Icons.Default.Done
    }
}

@Preview()
@Composable
fun Preview(){
    MyApplicationTheme {
        JutopiaApp()
    }
}