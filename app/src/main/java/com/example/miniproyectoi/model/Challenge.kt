package com.example.miniproyectoi.model

import com.google.firebase.firestore.DocumentId
import java.io.Serializable

data class Challenge(
    @DocumentId
    val id: String = "",
    var name: String = ""
) : Serializable
