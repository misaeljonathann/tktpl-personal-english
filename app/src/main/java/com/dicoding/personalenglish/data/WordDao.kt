package com.dicoding.personalenglish.data

import androidx.lifecycle.LiveData
import androidx.room.*

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
}