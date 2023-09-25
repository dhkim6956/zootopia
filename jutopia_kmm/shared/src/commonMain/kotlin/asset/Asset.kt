@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class,
    ExperimentalMaterialApi::class, ExperimentalResourceApi::class
)

package asset

import BottomTabBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import common.TopPageBar
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun Asset(navigator: Navigator) {

    Column {
        TopPageBar("자산")

        Chips(navigator)
    }
    BottomTabBar(navigator)
}

@Composable
fun Chips(navigator: Navigator ) {

    Row (
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        FilterChip(
            onClick = { navigator.navigate("/asset/deposit") },
            selected = true,
            colors = ChipDefaults.filterChipColors(
                backgroundColor = Color(0xFFF1D3FB),
                contentColor = Color(0xFFAF30C9)
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource("drawable/chip_deposit.xml"),
                    contentDescription = "chip_deposit"
                )
            }
        ) {
            Text("입출금")
        }
        FilterChip(
            onClick = { navigator.navigate("/asset/save") },
            selected = true,
            colors = ChipDefaults.filterChipColors(
                backgroundColor = Color(0xFFDFDBF9),
                contentColor = Color(0xFF7E51D6)
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource("drawable/chip_save.xml"),
                    contentDescription = "chip_save"
                )
            }
        ) {
            Text("적금")
        }
        FilterChip(
            onClick = { navigator.navigate("/asset/point") },
            selected = true,
            colors = ChipDefaults.filterChipColors(
                backgroundColor = Color(0xFFCBD8F2),
                contentColor = Color(0xFF4963C7)
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource("drawable/chip_point.xml"),
                    contentDescription = "chip_point"
                )
            }
        ) {
            Text("포인트")
        }
        FilterChip(
            onClick = { navigator.navigate("/asset/stock") },
            selected = true,
            colors = ChipDefaults.filterChipColors(
                backgroundColor = Color(0xFFB7E7FF),
                contentColor = Color(0xFF0087D1)
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource("drawable/chip_stock.xml"),
                    contentDescription = "chip_stock"
                )
            }
        ) {
            Text("주식")
        }
        FilterChip(
            onClick = { navigator.navigate("/asset/building") },
            selected = true,
            colors = ChipDefaults.filterChipColors(
                backgroundColor = Color(0xFFC8EAC9),
                contentColor = Color(0xFF358438)
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource("drawable/chip_building.xml"),
                    contentDescription = "chip_building"
                )
            }
        ) {
            Text("부동산")
        }
    }
}