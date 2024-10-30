package com.example.miniproyectoi.respository
import android.content.Context
import com.example.miniproyectoi.data.ChallengeDB
import com.example.miniproyectoi.data.ChallengeDao
import com.example.miniproyectoi.model.Challenge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChallengeRepository(val context: Context) {
    private var challengeDao: ChallengeDao = ChallengeDB.getDatabase(context).challengeDao()
    suspend fun saveChallenge(challenge: Challenge){
        withContext(Dispatchers.IO){
            challengeDao.saveChallenge(challenge)
        }
    }

    suspend fun getListChallenge(): MutableList<Challenge>{
        return withContext(Dispatchers.IO){
            challengeDao.getListChallenge()
        }
    }

    suspend fun deleteChallenge(challenge: Challenge){
        withContext(Dispatchers.IO){
            challengeDao.deleteChallenge(challenge)
        }
    }

    suspend fun updateRepository(challenge: Challenge){
        withContext(Dispatchers.IO){
            challengeDao.updateChallenge(challenge)
        }
    }
}