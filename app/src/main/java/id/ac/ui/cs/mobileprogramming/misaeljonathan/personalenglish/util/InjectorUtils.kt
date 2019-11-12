package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.util

import android.content.Context
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.data.ChallengeDatabase
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.data.WordDatabase
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.repository.ChallengeRepository
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.repository.WordRepository
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.ui.ChallengeViewModelFactory
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.ui.WordViewModelFactory

object InjectorUtils {

    fun provideWordViewModelFactory(context: Context): WordViewModelFactory {

        val wordRepository = WordRepository.getInstance(WordDatabase.getInstance(context).wordDao())
        return WordViewModelFactory(wordRepository)
    }

    fun provideChallengeViewModelFactory(context: Context): ChallengeViewModelFactory {

        val challengeRepository = ChallengeRepository.getInstance(ChallengeDatabase.getInstance(context).challengeDao())
        return ChallengeViewModelFactory(challengeRepository)
    }
}