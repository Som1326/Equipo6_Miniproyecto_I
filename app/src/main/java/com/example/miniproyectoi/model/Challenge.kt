package com.example.miniproyectoi.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Challenge(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String): Serializable
