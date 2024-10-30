package com.example.miniproyectoi.data
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.miniproyectoi.model.Challenge

@Dao
interface ChallengeDao {
    @Query("SELECT * FROM Challenge")
    suspend fun getListChallenge(): MutableList<Challenge>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveChallenge(challenge: Challenge)

    @Update
    suspend fun updateChallenge(challenge: Challenge)

    @Delete
    suspend fun deleteChallenge(challenge: Challenge)
}