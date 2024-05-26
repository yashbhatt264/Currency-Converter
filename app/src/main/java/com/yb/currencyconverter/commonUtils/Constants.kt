package com.yb.currencyconverter.commonUtils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.yb.currencyconverter.models.CurrencyWithCountryName

/**
 * @author BHATTJI
 * Created 25-05-2024 at 12:19 pm
 */
class Constants {
    companion object{

        val FROM = "FROM"

        val TO = "TO"

        val DEFAULT = "DEFAULT"

        val CurrencyCodeList = listOf(
            CurrencyWithCountryName("Australia", "AUD"),
            CurrencyWithCountryName("Brazil", "BRL"),
            CurrencyWithCountryName("Bulgaria", "BGN"),
            CurrencyWithCountryName("Canada", "CAD"),
            CurrencyWithCountryName("China", "CNY"),
            CurrencyWithCountryName("Croatia", "HRK"),
            CurrencyWithCountryName("Czech Republic", "CZK"),
            CurrencyWithCountryName("Denmark", "DKK"),
            CurrencyWithCountryName("European Union", "EUR"),
            CurrencyWithCountryName("Great Britain", "GBP"),
            CurrencyWithCountryName("Hong Kong", "HKD"),
            CurrencyWithCountryName("Hungary", "HUF"),
            CurrencyWithCountryName("Iceland", "ISK"),
            CurrencyWithCountryName("India", "INR"),
            CurrencyWithCountryName("Indonesia", "IDR"),
            CurrencyWithCountryName("Israel", "ILS"),
            CurrencyWithCountryName("Japan", "JPY"),
            CurrencyWithCountryName("Korea", "KRW"),
            CurrencyWithCountryName("Malaysia", "MYR"),
            CurrencyWithCountryName("Mexico", "MXN"),
            CurrencyWithCountryName("New Zealand", "NZD"),
            CurrencyWithCountryName("Norway", "NOK"),
            CurrencyWithCountryName("Philippines", "PHP"),
            CurrencyWithCountryName("Poland", "PLN"),
            CurrencyWithCountryName("Romania", "RON"),
            CurrencyWithCountryName("Russia", "RUB"),
            CurrencyWithCountryName("Singapore", "SGD"),
            CurrencyWithCountryName("South Africa", "ZAR"),
            CurrencyWithCountryName("Sweden", "SEK"),
            CurrencyWithCountryName("Switzerland", "CHF"),
            CurrencyWithCountryName("Thailand", "THB"),
            CurrencyWithCountryName("Turkey", "TRY"),
            CurrencyWithCountryName("United States", "USD"),
        )

         fun checkInternetConnection(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }

        }
    }
}