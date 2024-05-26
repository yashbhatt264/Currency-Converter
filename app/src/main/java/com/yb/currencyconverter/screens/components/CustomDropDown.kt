package com.yb.currencyconverter.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.radiusagent.brokerage.presentation.ui.theme.montserrat

/**
 * @author BHATTJI
 * Created 26-05-2024 at 12:22 pm
 */
@Composable
fun CustomDropDown(
    value: String,
    label: String,
    clicked: () -> Unit,
    modifier: Modifier = Modifier,
    updateValue: (value: String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            updateValue(it)
        },
        shape = RoundedCornerShape(4.dp),
        placeholder = {
            Text(
                text = label,
                fontFamily = montserrat,
                fontSize = 14.sp,
                color = Color.LightGray
            )
        },
        label = {
            Text(
                text = label,
                fontFamily = montserrat,
                fontSize = 14.sp,
                color = Color.LightGray
            )
        },
        maxLines = 1,
        readOnly = true,
        enabled = false,
        textStyle = TextStyle(
            fontFamily = montserrat,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary
        ),
        colors = TextFieldDefaults.colors(
            cursorColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
        ),
        trailingIcon = {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(6.dp)
                    .width(16.dp)
                    .height(14.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    tint = Color.White,
                    contentDescription = ""
                )
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable {
                clicked()
            }
    )
}