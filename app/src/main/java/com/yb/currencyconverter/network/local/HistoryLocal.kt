package com.yb.currencyconverter.network.local

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.yb.currencyconverter.models.ConversionRates
import com.yb.currencyconverter.network.local.converters.AppTypeConverter

/**
 * @author BHATTJI
 * Created 25-05-2024 at 05:32 pm
 */
@Keep
@Entity
@TypeConverters(AppTypeConverter::class)
data class HistoryLocal(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "from_currency") val fromCurrency: String?,
    @ColumnInfo(name = "from") val from: String?,
    @ColumnInfo(name = "to_currency") val toCurrency: String?,
    @ColumnInfo(name = "to") val to: String?,
)
