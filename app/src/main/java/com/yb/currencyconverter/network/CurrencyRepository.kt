package com.yb.currencyconverter.network

import com.yb.currencyconverter.models.CurrencyConvertResponseDTO
import com.yb.currencyconverter.network.local.CurrencyRateLocal
import com.yb.currencyconverter.network.local.HistoryLocal
import retrofit2.Response
import kotlinx.coroutines.flow.Flow
/**
 * @author BHATTJI
 * Created 25-05-2024 at 02:57 pm
 */
interface CurrencyRepository {

    suspend fun getExchangeRates(country: String): Response<CurrencyConvertResponseDTO>

    suspend fun getExchangeRatesFromLocal(country: String): Flow<CurrencyRateLocal>

    suspend fun insertCurrencyToLocal(currencyRateLocal: CurrencyRateLocal)

    suspend fun insertToHistory(historyLocal: List<HistoryLocal>)

    suspend fun getHistoryFromLocal() : Flow<List<HistoryLocal>>
}