package com.yb.currencyconverter.screens.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yb.currencyconverter.commonUtils.Constants
import com.yb.currencyconverter.network.local.HistoryLocal
import kotlinx.coroutines.launch

/**
 * @author BHATTJI
 * Created 25-05-2024 at 03:57 pm
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryBottomSheet(
    list: List<HistoryLocal>,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = rememberModalBottomSheetState(true),
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        dragHandle = null) {
        LazyColumn(
            reverseLayout = true,
            modifier = Modifier
                .imePadding()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(18.dp)
        ) {
            items(list) { item ->

                Divider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth().height(1.dp)
                )

                Text(
                    text = "${item.fromCurrency}\t ${item.from} \n${item.toCurrency}\t ${item.to}",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(vertical = 10.dp)

                )
            }
        }
    }
}