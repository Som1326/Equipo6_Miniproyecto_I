package com.example.miniproyectoi.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.miniproyectoi.model.Challenge
import com.example.miniproyectoi.utils.Constants.NAME_DB

@Database(entities = [Challenge::class], version = 1)
abstract class ChallengeDB : RoomDatabase() {
    abstract fun challengeDao(): ChallengeDao

    companion object{
        fun getDatabase(context: Context): ChallengeDB {
            return Room.databaseBuilder(
                context.applicationContext,
                ChallengeDB::class.java,
                NAME_DB
            ).build()
        }
    }
}