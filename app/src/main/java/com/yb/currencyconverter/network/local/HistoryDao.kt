package com.yb.currencyconverter.network.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yb.currencyconverter.models.CurrencyConvert
import kotlinx.coroutines.flow.Flow

/**
 * @author BHATTJI
 * Created 25-05-2024 at 05:22 pm
 */
@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun pushToHistory(historyLocal: List<HistoryLocal>)

    @Query("Select * from HistoryLocal")
    fun getHistoryList(): Flow<List<HistoryLocal>>
}