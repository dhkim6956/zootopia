package school

import BottomTabBar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.TopPageBar
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.viewmodel.viewModel

@Composable
fun School(navigator: Navigator, viewModel : SchoolViewModel = viewModel(modelClass = SchoolViewModel::class) {
    SchoolViewModel()
}) {
    Column {
        TopPageBar("학교", navigator, showReturn = false)

        Notification(viewModel, navigator)

    }
    BottomTabBar(navigator)
}

@Composable
fun Notification(viewModel: SchoolViewModel, navigator: Navigator) {
    LazyColumn (
        modifier = Modifier
            .padding(20.dp)
    ) {
        items(viewModel.notice.value) {notiDetail ->
            Column (
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(
                        top = 4.dp,
                        bottom = 4.dp
                    ).clickable { navigator.navigate("/notice/1") }
            ) {
                Text(notiDetail.title, fontSize = 28.sp)
                Row (
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Top,
                ) {
                    Text(notiDetail.date, color = Color(0xFF9E9E9E))
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(notiDetail.time, color = Color(0xFF9E9E9E))
                }
                Divider(thickness = 2.dp , color = Color(0x22000000))
            }
        }
    }
}