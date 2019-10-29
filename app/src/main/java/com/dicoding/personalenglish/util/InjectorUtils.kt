package com.dicoding.personalenglish.util

import android.content.Context
import com.dicoding.personalenglish.Repository.WordRepository
import com.dicoding.personalenglish.data.WordDatabase
import com.dicoding.personalenglish.ui.WordViewModel
import com.dicoding.personalenglish.ui.WordViewModelFactory

object InjectorUtils {

    fun provideQuotesViewModelFactory(context: Context): WordViewModelFactory {

        val wordRepository = WordRepository.getInstance(WordDatabase.getInstance(context).wordDao())
        return WordViewModelFactory(wordRepository)
    }
}