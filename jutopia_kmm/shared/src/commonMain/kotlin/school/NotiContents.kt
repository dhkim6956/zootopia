package school

import BottomTabBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.TopPageBar
import icejaramFontFamily
import icesiminFontFamily
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel

@Composable
fun NotiContents(navigator: Navigator, idx: Int) {
    Column {
        TopPageBar("공지사항 상세", navigator)

        val contentViewModel =
            viewModel(modelClass = NotiContentsViewModel::class, keys = listOf(idx)) {
                NotiContentsViewModel(idx)
            }

        Contents(contentViewModel)
    }
    BottomTabBar(navigator)
}

@Composable
fun Contents(viewModel: NotiContentsViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(
                top = 12.dp,
                start = 12.dp,
                bottom = 80.dp,
                end = 12.dp
            )
            .shadow(
                8.dp,
                shape = AbsoluteCutCornerShape(bottomRight = 40.dp),
                ambientColor = Color.Gray
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    color = Color(0xFFFFE585),
                    shape = AbsoluteCutCornerShape(bottomRight = 40.dp)
                )
                .padding(12.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        viewModel.notice.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        fontFamily = icesiminFontFamily
                    )
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(viewModel.notice.date)
                        Text(viewModel.notice.time)
                    }
                }
                Divider()
                Text(viewModel.notice.detail, fontSize = 24.sp, fontFamily = icejaramFontFamily)
            }
        }
    }
}