package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.tools.Converters
import java.sql.Date

@Entity(tableName = "challenge_table")
@TypeConverters(Converters::class)
data class Challenge (
    @ColumnInfo(name="title") var title: String,
    @ColumnInfo(name="due_date") var dueDate: Date,
    @ColumnInfo(name="current_count") var currentCount: Int,
    @ColumnInfo(name="target") var target: Int
){
    @PrimaryKey(autoGenerate = true)
    var challengeId: Int = 0
}

