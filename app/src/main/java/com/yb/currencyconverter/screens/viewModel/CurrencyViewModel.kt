package com.yb.currencyconverter.screens.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.yb.currencyconverter.models.ConversionRates
import com.yb.currencyconverter.network.local.HistoryLocal
import com.yb.currencyconverter.network.local.PreferenceDataUtils
import com.yb.currencyconverter.screens.CurrencyState
import com.yb.currencyconverter.use_case.GetExchangeRateUseCase
import com.yb.currencyconverter.use_case.HistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author BHATTJI
 * Created 25-05-2024 at 01:35 pm
 */
@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val getExchangeRateUseCase: GetExchangeRateUseCase,
    private val historyUseCase: HistoryUseCase,
    private val preferenceDataStoreUtils: PreferenceDataUtils,
    private val application: Application
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(CurrencyState())
    var state = _state.asStateFlow()

    init {
        fetchHistoryFromLocal()
        viewModelScope.launch {
            _state.update {
                it.copy(
                    defaultCurrency = preferenceDataStoreUtils.getDefault().first(),
                    baseCurrency = preferenceDataStoreUtils.getDefault().first()
                )
            }
            getExchangeRatesSafeCall(preferenceDataStoreUtils.getDefault().first())
        }
    }

    fun updateDropDownValues(countryCurrencyCode: String, isForBase: String) {
        if (isForBase == "FROM") {
            getExchangeRatesSafeCall(value = countryCurrencyCode)
        }
        _state.update {
            when (isForBase) {
                "FROM" -> {
                    it.copy(
                        baseCurrency = countryCurrencyCode
                    )
                }

                "DEFAULT" -> {
                    it.copy(
                        defaultCurrency = countryCurrencyCode,
                        baseCurrency = countryCurrencyCode
                    )
                }

                else -> {
                    it.copy(
                        toCurrency = countryCurrencyCode
                    )
                }
            }
        }

        if (isForBase == "DEFAULT") {
            getExchangeRatesSafeCall(countryCurrencyCode)
            viewModelScope.launch {
                preferenceDataStoreUtils.setDefault(countryCurrencyCode)
            }
        }
    }

    fun updateAmount(value: String) {
        if (value.isNotEmpty()) {

            _state.update {
                it.copy(
                    amount = value,
                    convertedValue = "${
                        getOutputString(
                            if (value == "1") getToValue(
                                it.toCurrency,
                                it.currencyRate.conversionRates
                            ) else value.toDouble() * getToValue(
                                it.toCurrency,
                                it.currencyRate.conversionRates
                            )
                        )
                    } ${it.toCurrency}",
                    singleConvertedValue = "1 ${it.baseCurrency} = ${
                        getOutputString(
                            getToValue(
                                it.toCurrency,
                                it.currencyRate.conversionRates
                            )
                        )
                    } ${it.toCurrency}"
                )
            }
        }
    }

    fun swapTargets() {
        getExchangeRatesSafeCall(state.value.toCurrency) {
            val temp = state.value.baseCurrency
            _state.update {
                it.copy(
                    baseCurrency = it.toCurrency,
                    toCurrency = temp,
                    amount = it.amount,
                    convertedValue = "${
                        getOutputString(
                            if (it.amount == "1") getToValue(
                                temp,
                                it.currencyRate.conversionRates
                            ) else it.amount.toDouble() * getToValue(
                                temp,
                                it.currencyRate.conversionRates
                            )
                        )
                    } ${temp}",
                    singleConvertedValue = "1 ${it.toCurrency} = ${
                        getOutputString(
                            getToValue(
                                temp,
                                it.currencyRate.conversionRates
                            )
                        )
                    } ${temp}"
                )
            }
        }
    }

    fun doCalculation() {
        _state.update {
            it.copy(
                convertedValue = "${
                    getOutputString(
                        it.amount.toDouble() * getToValue(
                            it.toCurrency,
                            it.currencyRate.conversionRates
                        )
                    )
                } ${it.toCurrency}",
                singleConvertedValue = "1 ${it.baseCurrency} = ${
                    getOutputString(
                        getToValue(
                            it.toCurrency,
                            it.currencyRate.conversionRates
                        )
                    )
                } ${it.toCurrency}"
            )
        }

        pushDataToLocal()
    }

    private fun pushDataToLocal() {
        _state.update {
            it.copy(
                historyList = it.historyList.plus(
                    HistoryLocal(
                        state.value.historyList.size + 1,
                        fromCurrency = state.value.baseCurrency,
                        from = state.value.amount,
                        toCurrency = state.value.toCurrency,
                        to = state.value.convertedValue
                    )
                ).toMutableList()
            )
        }

        historyUseCase.invoke(
            historyLocal = state.value.historyList
        ).onEach {
            Log.e("RESULT IS:", it.toString())
            fetchHistoryFromLocal()
        }.launchIn(viewModelScope)
    }

    private fun fetchHistoryFromLocal(){
        historyUseCase.getHistoryFromLocal().onEach { result->
            Log.e("RESULT IS:", result.toString())

            _state.update {
                it.copy(
                    historyList = result.toMutableList()
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun getExchangeRatesSafeCall(
        value: String,
        isSucceed: (success: Boolean) -> Unit = {}
    ) {
        getExchangeRateUseCase.invoke(value, application.applicationContext).onEach { data ->
            _state.update {
                it.copy(
                    currencyRate = data
                )
            }
            isSucceed(true)
        }.launchIn(viewModelScope)
    }

    private fun getOutputString(value: Double): String {
        return "%.2f".format(value)
    }

    private fun getToValue(currencyCode: String, rates: ConversionRates?): Double {
        return when (currencyCode) {
            "AUD" -> rates?.aUD ?: 0.0
            "BRL" -> rates?.bRL ?: 0.0
            "BGN" -> rates?.bGN ?: 0.0
            "CAD" -> rates?.cAD ?: 0.0
            "CNY" -> rates?.cNY ?: 0.0
            "HRK" -> rates?.hRK ?: 0.0
            "CZK" -> rates?.cZK ?: 0.0
            "DKK" -> rates?.dKK ?: 0.0
            "EUR" -> rates?.eUR ?: 0.0
            "GBP" -> rates?.gBP ?: 0.0
            "HKD" -> rates?.hKD ?: 0.0
            "HUF" -> rates?.hUF ?: 0.0
            "ISK" -> rates?.iSK ?: 0.0
            "INR" -> rates?.iNR ?: 0.0
            "IDR" -> rates?.iDR ?: 0.0
            "ILS" -> rates?.iLS ?: 0.0
            "JPY" -> rates?.jPY ?: 0.0
            "KRW" -> rates?.kRW ?: 0.0
            "MYR" -> rates?.mYR ?: 0.0
            "MXN" -> rates?.mXN ?: 0.0
            "NZD" -> rates?.nZD ?: 0.0
            "NOK" -> rates?.nOK ?: 0.0
            "PHP" -> rates?.pHP ?: 0.0
            "PLN" -> rates?.pLN ?: 0.0
            "RON" -> rates?.rON ?: 0.0
            "RUB" -> rates?.rUB ?: 0.0
            "SGD" -> rates?.sGD ?: 0.0
            "ZAR" -> rates?.zAR ?: 0.0
            "SEK" -> rates?.sEK ?: 0.0
            "CHF" -> rates?.cHF ?: 0.0
            "THB" -> rates?.tHB ?: 0.0
            "TRY" -> rates?.tRY ?: 0.0
            "USD" -> rates?.uSD ?: 0.0
            else -> 0.00
        }
    }

}