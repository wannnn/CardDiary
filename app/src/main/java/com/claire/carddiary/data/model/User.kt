package com.claire.carddiary.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    val id: Long = 0,
    val firstName: String = "",
    val lastName: String = "",
    val picture: String = ""
)