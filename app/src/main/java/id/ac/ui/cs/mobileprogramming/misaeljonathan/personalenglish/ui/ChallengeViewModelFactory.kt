package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.repository.ChallengeRepository

class ChallengeViewModelFactory(private val challengeRepository: ChallengeRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        return ChallengeViewModel(
            challengeRepository
        ) as T
    }
}