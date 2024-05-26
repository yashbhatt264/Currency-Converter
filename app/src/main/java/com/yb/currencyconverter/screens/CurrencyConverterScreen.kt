package com.yb.currencyconverter.screens

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.radiusagent.brokerage.presentation.ui.theme.inter
import com.yb.currencyconverter.R
import com.yb.currencyconverter.commonUtils.Constants.Companion.DEFAULT
import com.yb.currencyconverter.commonUtils.Constants.Companion.FROM
import com.yb.currencyconverter.commonUtils.Constants.Companion.TO
import com.yb.currencyconverter.screens.bottomsheet.CurrencyBottomSheet
import com.yb.currencyconverter.screens.bottomsheet.HistoryBottomSheet
import com.yb.currencyconverter.screens.components.CustomDropDown
import com.yb.currencyconverter.screens.viewModel.CurrencyViewModel

/**
 * @author BHATTJI
 * Created 25-05-2024 at 12:31 pm
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyConverterScreen(
    context: Context, viewModel: CurrencyViewModel
) {

    val uiState by viewModel.state.collectAsState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var showHistoryBottomSheet by remember { mutableStateOf(false) }
    var type by remember { mutableStateOf("") }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Currency Converter Project",
                        fontSize = 24.sp,
                        fontFamily = inter,
                        color = MaterialTheme.colorScheme.primary,
                        style = TextStyle(
                            fontWeight = FontWeight.Black,
                            lineHeight = 30.sp
                        )
                    )
                },
            )
        },
    ) { _ ->
        Column(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),

            ) {

            Spacer(modifier = Modifier.padding(48.dp))

            CustomDropDown(
                value = uiState.defaultCurrency,
                label = stringResource(id = R.string.select_your_default_preference),
                clicked = {
                    type = DEFAULT
                    showBottomSheet = true
                },
                updateValue = {
                    type = DEFAULT
                    showBottomSheet = true
                }
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Row {
                CustomDropDown(
                    value = uiState.baseCurrency,
                    label = FROM,
                    clicked = {
                        type = FROM
                        showBottomSheet = true
                    },
                    updateValue = {
                        type = FROM
                        showBottomSheet = true
                    },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                CustomDropDown(
                    value = uiState.toCurrency,
                    label = TO,
                    clicked = {
                        type = TO
                        showBottomSheet = true
                    },
                    updateValue = {
                        type = TO
                        showBottomSheet = true
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.padding(10.dp))

            OutlinedTextField(
                label = {
                    Text(
                        text = stringResource(R.string.amount),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                value = uiState.amount,
                onValueChange = {
                    viewModel.updateAmount(value = it)
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.padding(20.dp))

            Row {
                Button(
                    onClick = {
                        viewModel.doCalculation()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .height(50.dp)
                ) {
                    Text(
                        stringResource(R.string.add_to_history),
                        fontSize = 10.sp,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Button(
                    onClick = {
                        viewModel.swapTargets()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .height(50.dp)
                ) {
                    Text(
                        stringResource(R.string.swap_currencies),
                        fontSize = 10.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.padding(12.dp))

            if (uiState.historyList.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.view_history),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            showHistoryBottomSheet = true
                        },
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
                    )
                )

                Spacer(modifier = Modifier.padding(30.dp))
            }

            Text(
                text = uiState.convertedValue,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.primary,
                style = TextStyle(
                    fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
                )
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = uiState.singleConvertedValue,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primaryContainer,
                style = TextStyle(textAlign = TextAlign.Center)
            )


        }

    }

    if (showBottomSheet) {
        CurrencyBottomSheet(
            onDismiss = {
                showBottomSheet = false
            },
            selectedCurrency = {
                viewModel.updateDropDownValues(countryCurrencyCode = it, isForBase = type)
                showBottomSheet = false

            }
        )
    }

    if (showHistoryBottomSheet) {
        HistoryBottomSheet(
            list = uiState.historyList,
            onDismiss = {
                showHistoryBottomSheet = false
            },
        )
    }
}