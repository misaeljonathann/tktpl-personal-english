package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.tools.Converters
import java.sql.Date

@Entity(tableName = "word_table")
@TypeConverters(Converters::class)
data class Word (
    @ColumnInfo(name="word") var word: String,
    @ColumnInfo(name="time") var time: Date
){
    @PrimaryKey(autoGenerate = true)
    var wordId: Int = 0
}

