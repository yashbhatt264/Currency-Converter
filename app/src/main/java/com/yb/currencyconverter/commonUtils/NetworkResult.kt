package com.yb.currencyconverter.commonUtils

/**
 * @author BHATTJI
 * Created 25-05-2024 at 01:37 pm
 */
sealed class NetworkResult<T>(val data: T? = null, val message: String? = null){
    class Success<T>(data: T): NetworkResult<T>(data)
    class Error<T>(data: T? = null, message: String?): NetworkResult<T>(data, message)
    class Loading<T>(): NetworkResult<T>()
}