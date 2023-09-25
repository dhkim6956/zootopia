package common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import icehimchanFontFamily
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TopPageBar(navLoc: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth()
            .height(80.dp)
            .background(color = Variables.ColorsBackground)
            .padding(start = 40.dp, top = 16.dp, end = 40.dp, bottom = 16.dp)
    ) {
        Text(navLoc, fontFamily = icehimchanFontFamily, fontSize = 28.sp)
        Image(
            painterResource("drawable/text_bubble.xml"),
            null,
            modifier = Modifier.height(40.dp)
        )
    }
}