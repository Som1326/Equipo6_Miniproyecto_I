package com.example.miniproyectoi.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "challenge")
data class Challenge(
    @PrimaryKey
    val id: String = "",
    var name: String = ""
) : Serializable

