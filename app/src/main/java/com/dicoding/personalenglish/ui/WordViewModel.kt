package com.dicoding.personalenglish.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.personalenglish.Repository.WordRepository
import com.dicoding.personalenglish.data.Word

class WordViewModel(
    private val wordRepository: WordRepository
): ViewModel() {
    private val allWords: LiveData<List<Word>> = wordRepository.getAllWord()

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
}