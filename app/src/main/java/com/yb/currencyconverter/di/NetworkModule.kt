package com.yb.currencyconverter.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.radiusagent.brokerage.common.APILogger
import com.yb.currencyconverter.network.CurrencyAPI
import com.yb.currencyconverter.network.CurrencyRepository
import com.yb.currencyconverter.network.CurrencyRepositoryImpl
import com.yb.currencyconverter.network.local.AppDatabase
import com.yb.currencyconverter.network.local.CurrencyDao
import com.yb.currencyconverter.network.local.HistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author BHATTJI
 * Created 25-05-2024 at 01:28 pm
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {

        val httpLoggingInterceptor = HttpLoggingInterceptor(APILogger())
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
            .readTimeout(15, TimeUnit.SECONDS).connectTimeout(15, TimeUnit.SECONDS).build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl("https://v6.exchangerate-api.com/v6/004c29242145a6f1c14af23e/")
            .addConverterFactory(gsonConverterFactory).client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    @Provides
    @Singleton
    fun providesCurrencyAPI(
        gson: Gson, client: OkHttpClient
    ): CurrencyAPI {
        return Retrofit.Builder().client(client)
            .baseUrl("https://v6.exchangerate-api.com/v6/004c29242145a6f1c14af23e/")
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
            .create(CurrencyAPI::class.java)
    }

    @Provides
    @Singleton
    fun providesCurrency(
        api: CurrencyAPI, currencyDao: CurrencyDao, historyDao: HistoryDao
    ): CurrencyRepository {
        return CurrencyRepositoryImpl(api, currencyDao, historyDao)
    }

    @Provides
    @Singleton
    fun provideCurrencyDao(appDatabase: AppDatabase): CurrencyDao {
        return appDatabase.currencyDao()
    }

    @Provides
    @Singleton
    fun provideHistoryDao(appDatabase: AppDatabase): HistoryDao {
        return appDatabase.historyDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java, "currency-db"
        ).fallbackToDestructiveMigration().build()
    }

}