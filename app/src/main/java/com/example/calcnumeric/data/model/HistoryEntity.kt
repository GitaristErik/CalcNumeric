package com.example.calcnumeric.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.calcnumeric.domain.entity.History
import java.util.Date

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "expression") val expression: String,
    @ColumnInfo(name = "result") val result: String,
    @ColumnInfo(name = "date") val date: Long = Date().time
)

fun HistoryEntity.toDomain() = History(
    expression, result, date
)

fun History.toEntity() = HistoryEntity(
    expression, result, date
)