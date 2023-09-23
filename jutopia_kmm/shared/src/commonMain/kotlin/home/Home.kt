package home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun Home(navigator: Navigator) {
    Column {
        Text(text = "Home")
        Button(onClick = { navigator.navigate("/asset") }) {
            Text("asset")
        }
        Button(onClick = { navigator.navigate("/menus") }) {
            Text("menus")
        }
        Button(onClick = { navigator.navigate("/news") }) {
            Text("news")
        }
        Button(onClick = { navigator.navigate("/school") }) {
            Text("school")
        }
    }
}