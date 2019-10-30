package com.dicoding.personalenglish.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dicoding.personalenglish.Common.Common
import com.dicoding.personalenglish.tools.Converters
import java.sql.Date

@Entity(tableName = "word_table")
@TypeConverters(Converters::class)
data class Word (
    @ColumnInfo(name="word") var word: String,
    @ColumnInfo(name="time") var time: Date
){
    @PrimaryKey(autoGenerate = true)
    var wordId: Int = 0
    fun getType(): Int {
        return Common.VIEWTYPE_WORD.code
    }
}

