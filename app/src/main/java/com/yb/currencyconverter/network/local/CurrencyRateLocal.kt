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
data class CurrencyRateLocal(
    @PrimaryKey @ColumnInfo(name = "base_code") val baseCode: String,
    @ColumnInfo(name = "conversionRates") val conversionRates: ConversionRates?,
    @ColumnInfo(name = "documentation") val documentation: String?,
    @ColumnInfo(name = "result") val result: String?,
    @ColumnInfo(name = "terms_of_use") val termsOfUse: String?,
    @ColumnInfo(name = "time_last_update_unix") val timeLastUpdateUnix: Int?,
    @ColumnInfo(name = "time_last_update_utc") val timeLastUpdateUtc: String?,
    @ColumnInfo(name = "time_next_update_unix") val timeNextUpdateUnix: Int?,
    @ColumnInfo(name = "time_next_update_utc") val timeNextUpdateUtc: String?,
)
