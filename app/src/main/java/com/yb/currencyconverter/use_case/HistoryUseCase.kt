package com.yb.currencyconverter.use_case

import android.content.Context
import com.yb.currencyconverter.commonUtils.Constants.Companion.checkInternetConnection
import com.yb.currencyconverter.models.CurrencyConvert
import com.yb.currencyconverter.models.toConvert
import com.yb.currencyconverter.models.toLocal
import com.yb.currencyconverter.network.CurrencyRepository
import com.yb.currencyconverter.network.local.HistoryLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @author BHATTJI
 * Created 25-05-2024 at 03:00 pm
 */
class HistoryUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository,

)  {
    operator fun invoke(historyLocal: List<HistoryLocal>): Flow<Boolean> = flow{
        currencyRepository.insertToHistory(historyLocal)

        emit(true)
    }

    fun getHistoryFromLocal(): Flow<List<HistoryLocal>> = flow{
        emit(currencyRepository.getHistoryFromLocal().first())
    }
}