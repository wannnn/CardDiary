package com.claire.carddiary.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card")
data class Card(
    @PrimaryKey
    val cardId: Long = 0,
    val images: List<String> = listOf(),
    val title: String = "",
    val date: String = "",
    val weather: Int = 0,  // ex.(0:sunny 1:rainy)
    val content: String = "",
    val location: Location = Location()
) {
    data class Location(
        val cardId: Long = 0,
        val country: String = "",
        val city: String = ""
    )
}