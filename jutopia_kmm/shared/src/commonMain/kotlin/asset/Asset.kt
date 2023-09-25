@file:OptIn(ExperimentalResourceApi::class, ExperimentalMaterialApi::class)

package asset

import BottomTabBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import asset.subMenu.Building
import asset.subMenu.Deposit
import asset.subMenu.Point
import asset.subMenu.Save
import asset.subMenu.Stock
import common.TopPageBar
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

data class chipItem(val idx: Int, val name: String, val bgColor: Color, val conColor: Color, val desc: String)

val chipItems: List<chipItem> = listOf(
    chipItem(0, "deposit", Color(0xFFF1D3FB), Color(0xFFAF30C9), "입출금"),
    chipItem(1, "save", Color(0xFFDFDBF9), Color(0xFF7E51D6), "적금"),
    chipItem(2, "point", Color(0xFFCBD8F2), Color(0xFF4963C7), "포인트"),
    chipItem(3, "stock", Color(0xFFB7E7FF), Color(0xFF0087D1), "주식"),
    chipItem(4, "building", Color(0xFFC8EAC9), Color(0xFF358438), "부동산")
)

@Composable
fun Asset(navigator: Navigator, category: Int?, viewModel: AssetViewModel = viewModel(modelClass = AssetViewModel::class) {savedStateHolder ->
    AssetViewModel(savedStateHolder)
}) {
    if(category != null) viewModel.setChipIdx(category)

    Column {
        TopPageBar("자산", navigator, showReturn = false)

        Chips(navigator, viewModel.chipIdx.value, viewModel)

        when (viewModel.chipIdx.value) {
            0 -> Deposit()
            1 -> Save()
            2 -> Point()
            3 -> Stock()
            4 -> Building()
            else -> Text("Error Page")
        }
    }
    BottomTabBar(navigator)
}

@Composable
fun Chips(navigator: Navigator, selectedIdx: Int, viewModel: AssetViewModel ) {
    Box(
        modifier = Modifier
            .padding(12.dp)
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(chipItems) { item ->
                FilterChip(
                    onClick = { viewModel.setChipIdx(item.idx) },
                    selected = false,
                    colors = ChipDefaults.filterChipColors(
                        backgroundColor = if (item.idx == viewModel.chipIdx.value) item.bgColor else item.bgColor.copy(alpha = 0.4F),
                        contentColor = if (item.idx == viewModel.chipIdx.value) item.conColor else item.conColor.copy(alpha = 0.4F)
                    ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource("drawable/chip_" + item.name + ".xml"),
                            contentDescription = "chip_" + item.name
                        )
                    }
                ) {
                    Text(item.desc)
                }
            }
        }
    }
}