package com.example.calcnumeric.data.datasource

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.calcnumeric.data.model.HistoryEntity

@Database(
    entities = [HistoryEntity::class],
    version = CalculatorDatabase.VERSION,
    exportSchema = false
)
abstract class CalculatorDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    companion object {
        @VisibleForTesting
        const val NAME = "CalcNumericDB"
        const val VERSION = 1

        const val TABLE_HISTORY = "history"
        const val COLUMN_SORT = "date"

        fun initialize(context: Context) = Room
            .databaseBuilder(context, CalculatorDatabase::class.java, NAME)
            .build()
    }
}