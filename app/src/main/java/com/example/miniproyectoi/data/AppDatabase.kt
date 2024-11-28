package com.example.miniproyectoi.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.miniproyectoi.model.Challenge
import com.example.miniproyectoi.utils.Constants.NAME_DB


@Database(entities = [Challenge::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun challengeDao(): ChallengeDao
}
