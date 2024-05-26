package com.yb.currencyconverter.network

import com.yb.currencyconverter.models.CurrencyConvertResponseDTO
import com.yb.currencyconverter.network.local.CurrencyDao
import com.yb.currencyconverter.network.local.CurrencyRateLocal
import com.yb.currencyconverter.network.local.HistoryDao
import com.yb.currencyconverter.network.local.HistoryLocal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

/**
 * @author BHATTJI
 * Created 25-05-2024 at 02:58 pm
 */
class CurrencyRepositoryImpl @Inject constructor(
    private val api: CurrencyAPI,
    private val currencyDao: CurrencyDao,
    private val historyDao: HistoryDao
) : CurrencyRepository {

    override suspend fun getExchangeRates(country: String): Response<CurrencyConvertResponseDTO> {
        return api.getExchangeRates(country)
    }

    override suspend fun getExchangeRatesFromLocal(country: String): Flow<CurrencyRateLocal> {
        return currencyDao.getExchangeRateFromLocal()
    }

    override suspend fun insertCurrencyToLocal(currencyRateLocal: CurrencyRateLocal) {
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            currencyDao.insertExchangeRates(currencyRateLocal)
        }
    }

    override suspend fun insertToHistory(historyLocal: List<HistoryLocal>) {
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            historyDao.pushToHistory(historyLocal)
        }
    }

    override suspend fun getHistoryFromLocal(): Flow<List<HistoryLocal>> {
        return historyDao.getHistoryList()
    }
}