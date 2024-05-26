package com.yb.currencyconverter.network.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yb.currencyconverter.models.CurrencyConvert


@Database(
    entities = [CurrencyRateLocal::class, HistoryLocal::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao

    abstract fun historyDao(): HistoryDao
}
