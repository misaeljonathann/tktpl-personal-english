package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.repository.WordRepository
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.data.Word

class WordViewModel(
    private val wordRepository: WordRepository
): ViewModel() {
    private val allWords: LiveData<List<Word>> = wordRepository.getAllWord()
    private val uniqueWords: LiveData<List<Word>> = wordRepository.getUnique()

    fun insert(word: Word) {
        wordRepository.insertWord(word)
    }

    fun update(word: Word) {
        wordRepository.updateWord(word)
    }

    fun delete(word: Word) {
        wordRepository.deleteWord(word)
    }

    fun getAllWords(): LiveData<List<Word>> {
        return allWords
    }

    fun getUnique(): LiveData<List<Word>> {
        return uniqueWords
    }

}