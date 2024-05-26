package com.yb.currencyconverter.screens

import com.yb.currencyconverter.models.CurrencyConvert
import com.yb.currencyconverter.network.local.HistoryLocal

/**
 * @author BHATTJI
 * Created 25-05-2024 at 03:05 pm
 */
data class CurrencyState(
 val currencyRate: CurrencyConvert = CurrencyConvert(),
 val baseCurrency: String = "",
 val defaultCurrency: String = "",
 val toCurrency: String = "",
 val amount: String = "",
 val convertedValue: String = "",
 val singleConvertedValue: String = "",
 val historyList: MutableList<HistoryLocal> = mutableListOf()
)