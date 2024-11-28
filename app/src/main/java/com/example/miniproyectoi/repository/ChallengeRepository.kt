package com.example.miniproyectoi.respository
import android.content.Context
import com.example.miniproyectoi.model.Challenge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChallengeRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val challengeCollection = firestore.collection("challenges")

    suspend fun saveChallenge(challengeName: String) {
        val challenge = Challenge(name = challengeName)
        challengeCollection.add(challenge).await()
    }

    suspend fun getListChallenge(): MutableList<Challenge> {
        return challengeCollection.get().await().toObjects(Challenge::class.java).toMutableList()
    }

    suspend fun deleteChallenge(challenge: Challenge) {
        challenge.id.takeIf { it.isNotEmpty() }?.let {
            challengeCollection.document(it).delete().await()
        }
    }

    suspend fun updateRepository(challenge: Challenge) {
        challenge.id.takeIf { it.isNotEmpty() }?.let {
            challengeCollection.document(it).set(challenge).await()
        }
    }
}