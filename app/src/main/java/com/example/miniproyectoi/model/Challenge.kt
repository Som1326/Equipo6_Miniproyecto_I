package com.example.miniproyectoi.model

import androidx.room.Entity
import com.google.firebase.firestore.DocumentId
import java.io.Serializable

@Entity(tableName = "challenge")
data class Challenge(
    @DocumentId
    val id: String = "",
    var name: String = ""
) : Serializable

