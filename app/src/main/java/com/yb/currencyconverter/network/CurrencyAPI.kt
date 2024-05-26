package com.yb.currencyconverter.network

import com.yb.currencyconverter.models.CurrencyConvertResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * @author BHATTJI
 * Created 25-05-2024 at 01:24 pm
 */
interface CurrencyAPI {
    @GET("latest/{path}")
    suspend fun getExchangeRates(@Path ("path") path: String): Response<CurrencyConvertResponseDTO>
}