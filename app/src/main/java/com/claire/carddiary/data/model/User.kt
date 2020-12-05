package com.claire.carddiary.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    val uid: String = "",
    val displayName: String = "",
    val picture: String = "",
    val email: String = ""
)