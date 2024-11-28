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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChallenge(challenge: Challenge)

    @Query("SELECT * FROM challenge ORDER BY id DESC")
    fun getListChallenge(): MutableList<Challenge>

    @Query("SELECT * FROM challenge WHERE id = :id")
    fun getChallengeById(id: String): Challenge?

    @Update
    fun updateChallenge(challenge: Challenge)

    @Delete
    fun deleteChallenge(challenge: Challenge)
}

