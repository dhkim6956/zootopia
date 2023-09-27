package stock.stocktrade

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import stock.stocklist.Stock

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun StockBuyingPage(
    stock: Stock,
    viewModel: StockTradeViewModel = moe.tlaster.precompose.viewmodel.viewModel(
        StockTradeViewModel::class
    ) { StockTradeViewModel() }
) {

    var orderQuantity by remember { mutableStateOf("1") }
    val stockPrice = stock.price
    var orderPrice by remember { mutableStateOf("${stockPrice}") }
    val keyboardController = LocalSoftwareKeyboardController.current
    var showDialog by remember { mutableStateOf(false) }

    val totalAmount: Double =
        if (orderQuantity.isNotBlank() && orderPrice.isNotBlank()) {
            orderQuantity.toDouble().times(orderPrice.toDouble())
        } else {
            0.0
        }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedTextField(
            value = orderQuantity,
            onValueChange = {
                if (it.all { char -> char.isDigit() }) {
                    orderQuantity = it
                }
            },
            label = { Text("주문량") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = orderPrice,
            onValueChange = {
                if (it.all { char -> char.isDigit() || char == '.' }) {
                    orderPrice = it
                }
            },
            label = { Text("주문가격") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        totalAmount?.let {
            Text(
                text = "총 주문 금액: ${it}",
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                showDialog = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "구매")
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Button(onClick = {
                        viewModel.tradeStock(
                            stock.copy(type = TradeType.Buy),
                            orderPrice.toDouble(),
                            orderQuantity.toInt()
                        )
                        orderQuantity = "1"
                        orderPrice = "${stock.price}"
                        showDialog = false
                    }) {
                        Text("구매")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("취소")
                    }
                },
                title = { Text("구매 확인") },
                text = {
                    Column {
                        Text("${stock.name}")
                        Text("가격: ${orderPrice}")
                        Text("구매량: ${orderQuantity}")
                        Text("총 금액: ${totalAmount}")
                    }
                }
            )
        }
    }
}
