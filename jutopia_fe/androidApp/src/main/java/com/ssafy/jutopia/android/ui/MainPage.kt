package com.ssafy.jutopia.android.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ssafy.jutopia.android.FeatureIcon
import com.ssafy.jutopia.android.Feature

@Composable
fun MainPage(
    featureOptions: List<Pair<Int, Feature>>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
                .weight(1f)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "총 포인트 : 1000p", fontSize = 24.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "총 화폐 : 500원", fontSize = 18.sp)
                Row(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Box(modifier = Modifier.clickable {
                        navController.navigate(Feature.History.name)
                    }){
                        Text(text = "거래내역")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Divider(
                        color = Color.Gray,
                        modifier = Modifier
                            .height(24.dp)
                            .width(1.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(modifier = Modifier.clickable {
                        navController.navigate(Feature.Export.name)
                    }){
                        Text(text = "송금")
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(40.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(2f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val rows = featureOptions.chunked(3)
            for (row in rows) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    for ((icon, menu) in row) {
                        FeatureIcon(icon, menu, navController)
                    }
                }
            }
        }

    }
}


