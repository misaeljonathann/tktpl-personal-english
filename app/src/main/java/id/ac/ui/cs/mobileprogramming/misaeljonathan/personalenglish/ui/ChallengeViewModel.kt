package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.data.Challenge
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.repository.ChallengeRepository

class ChallengeViewModel(
    private val challengeRepository: ChallengeRepository
): ViewModel() {
    private val allChallenge: LiveData<List<Challenge>> = challengeRepository.getAllChallenge()

    fun insert(challenge: Challenge) {
        challengeRepository.insertChallenge(challenge)
    }

    fun update(challenge: Challenge) {
        challengeRepository.updateChallenge(challenge)
    }

    fun delete(challenge: Challenge) {
        challengeRepository.deleteChallenge(challenge)
    }

    fun getAllChallenge(): LiveData<List<Challenge>> {
        return allChallenge
    }

    fun addWordCount() {
        challengeRepository.addWordCount()
    }
}