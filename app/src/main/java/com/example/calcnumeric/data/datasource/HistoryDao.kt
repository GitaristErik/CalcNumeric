package com.example.calcnumeric.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.calcnumeric.data.datasource.CalculatorDatabase.Companion.COLUMN_SORT
import com.example.calcnumeric.data.datasource.CalculatorDatabase.Companion.TABLE_HISTORY
import com.example.calcnumeric.data.model.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(history: HistoryEntity)

    @Query("SELECT * FROM $TABLE_HISTORY ORDER BY $COLUMN_SORT ASC")
    fun getAll(): Flow<List<HistoryEntity>>

    @Query("DELETE FROM $TABLE_HISTORY WHERE 1")
    suspend fun deleteAll()

    @Query("DELETE FROM $TABLE_HISTORY WHERE expression like :expression")
    suspend fun deleteByExpression(expression: String?)
}