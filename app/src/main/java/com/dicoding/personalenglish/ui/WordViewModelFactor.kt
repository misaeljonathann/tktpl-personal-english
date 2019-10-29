package com.dicoding.personalenglish.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.personalenglish.Repository.WordRepository

class WordViewModelFactory(private val wordRepository: WordRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        return WordViewModel(wordRepository) as T
    }
}