package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.data

import androidx.lifecycle.LiveData
import androidx.room.*
import java.sql.Date

@Dao
interface WordDao {

    @Query("SELECT * FROM word_table ORDER BY time ASC")
    fun getAll(): LiveData<List<@JvmSuppressWildcards Word>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(word: Word)

    @Delete
    fun delete(word: Word)

    @Update
    fun update(word: Word)

    @Query("SELECT * FROM word_table GROUP BY word ORDER BY time ASC")
    fun getUnique(): LiveData<List<Word>>
}