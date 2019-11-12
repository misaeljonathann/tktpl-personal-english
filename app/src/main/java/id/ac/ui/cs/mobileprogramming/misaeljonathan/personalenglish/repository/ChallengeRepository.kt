package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.repository

import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.data.Challenge
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.data.ChallengeDao
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.data.Word
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.data.WordDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext

class ChallengeRepository private constructor(
    private val challengeDao: ChallengeDao
) {
    private val allChallenge: LiveData<List<Challenge>> = challengeDao.getAll()

    var thread = newSingleThreadContext("wordRepo") as CoroutineDispatcher

    fun insertChallenge(challenge: Challenge) = GlobalScope.launch(thread) {
        challengeDao.insert(challenge)
    }

    fun getAllChallenge(): LiveData<List<Challenge>> {
        return allChallenge
    }

    fun deleteChallenge(challenge: Challenge) = GlobalScope.launch(thread) {
        challengeDao.delete(challenge)
    }

    fun updateChallenge(challenge: Challenge) = GlobalScope.launch(thread) {
        challengeDao.update(challenge)
    }

    fun addWordCount() = GlobalScope.launch(thread) {
        challengeDao.addWordCount()
    }

    companion object {
        @Volatile private var instance: ChallengeRepository? = null

        fun getInstance(challengeDao: ChallengeDao) =
            instance
                ?: synchronized(this) {
                    instance
                        ?: ChallengeRepository(
                            challengeDao
                        ).also { instance = it }
                }
    }
}
