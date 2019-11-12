package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ChallengeDao {

    @Query("SELECT * FROM challenge_table ORDER BY due_date ASC")
    fun getAll(): LiveData<List<@JvmSuppressWildcards Challenge>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(challenge: Challenge)

    @Delete
    fun delete(challenge: Challenge)

    @Update
    fun update(challenge: Challenge)

    @Query("UPDATE challenge_table SET current_count = current_count + 1 WHERE current_count < target")
    fun addWordCount()
}