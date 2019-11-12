package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.repository

import android.util.Log
import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.data.Word
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.data.WordDao
import kotlinx.coroutines.*

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

    fun getUnique(): LiveData<List<Word>> {
        val uniqueWord: LiveData<List<Word>> = wordDao.getUnique()
        return uniqueWord
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
            instance
                ?: synchronized(this) {
                instance
                    ?: WordRepository(
                        wordDao
                    ).also { instance = it }
            }
    }
}
