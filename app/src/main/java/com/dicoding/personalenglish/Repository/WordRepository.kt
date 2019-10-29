package com.dicoding.personalenglish.Repository

import androidx.lifecycle.LiveData
import com.dicoding.personalenglish.data.Word
import com.dicoding.personalenglish.data.WordDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext

class WordRepository private constructor(
    private val wordDao: WordDao
) {
    private val allWord: LiveData<List<Word>> = wordDao.getAll()

    var thread = newSingleThreadContext("wordRepo") as CoroutineDispatcher

    fun insertWord(word: Word) = GlobalScope.launch(thread) {
        wordDao.insert(word)
    }

    fun getAllWord(): LiveData<List<Word>> {
        return allWord
    }

    fun deleteWord(word: Word) = GlobalScope.launch(thread) {
        wordDao.delete(word)
    }

    fun updateWord(word: Word) = GlobalScope.launch(thread) {
        wordDao.update(word)
    }

    companion object {
        @Volatile private var instance: WordRepository? = null

        fun getInstance(wordDao: WordDao) =
            instance ?: synchronized(this) {
                instance ?: WordRepository(wordDao).also { instance = it }
            }
    }
}
