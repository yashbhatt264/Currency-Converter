package com.yb.currencyconverter.models


import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.yb.currencyconverter.network.local.CurrencyRateLocal
import com.yb.currencyconverter.network.local.converters.AppTypeConverter

@Keep
data class CurrencyConvertResponseDTO(
    @SerializedName("base_code")
    val baseCode: String ?= null,
    @SerializedName("conversion_rates")
    val conversionRates: ConversionRates? = null,
    @SerializedName("documentation")
    val documentation: String? = null,
    @SerializedName("result")
    val result: String? = null,
    @SerializedName("terms_of_use")
    val termsOfUse: String? = null,
    @SerializedName("time_last_update_unix")
    val timeLastUpdateUnix: Int? = null,
    @SerializedName("time_last_update_utc")
    val timeLastUpdateUtc: String? = null,
    @SerializedName("time_next_update_unix")
    val timeNextUpdateUnix: Int? = null,
    @SerializedName("time_next_update_utc")
    val timeNextUpdateUtc: String? = null
)


data class CurrencyConvert(
    @SerializedName("base_code")
    val baseCode: String ?= null,
    @SerializedName("conversion_rates")
    val conversionRates: ConversionRates? = null,
    @SerializedName("documentation")
    val documentation: String? = null,
    @SerializedName("result")
    val result: String? = null,
    @SerializedName("terms_of_use")
    val termsOfUse: String? = null,
    @SerializedName("time_last_update_unix")
    val timeLastUpdateUnix: Int? = null,
    @SerializedName("time_last_update_utc")
    val timeLastUpdateUtc: String? = null,
    @SerializedName("time_next_update_unix")
    val timeNextUpdateUnix: Int? = null,
    @SerializedName("time_next_update_utc")
    val timeNextUpdateUtc: String? = null
)

fun CurrencyConvertResponseDTO.toConvert() : CurrencyConvert{
    return CurrencyConvert(
        baseCode = baseCode,
        conversionRates = conversionRates,
        documentation = documentation,
        result = result,
        termsOfUse = termsOfUse,
        timeLastUpdateUnix = timeLastUpdateUnix,
        timeLastUpdateUtc = timeLastUpdateUtc,
        timeNextUpdateUnix = timeNextUpdateUnix
    )
}

fun CurrencyConvertResponseDTO.toLocal() : CurrencyRateLocal{
    return CurrencyRateLocal(
        baseCode = baseCode ?: "",
        conversionRates = conversionRates,
        documentation = documentation,
        result = result,
        termsOfUse = termsOfUse,
        timeLastUpdateUnix = timeLastUpdateUnix,
        timeLastUpdateUtc = timeLastUpdateUtc,
        timeNextUpdateUnix = timeNextUpdateUnix,
        timeNextUpdateUtc = timeNextUpdateUtc
    )
}