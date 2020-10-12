package com.claire.carddiary.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.claire.carddiary.data.source.local.CardTypeConverters
import com.claire.carddiary.utils.toSimpleDateFormat
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "card")
@TypeConverters(CardTypeConverters::class)
@Parcelize
data class Card(
    @PrimaryKey(autoGenerate = true)
    val cardId: Int = 0,
    val images: List<String> = listOf(),
    val title: String = "",
    val date: String = "",
    val weather: Int = 0,  // ex.(0:sunny 1:rainy)
    val content: String = "",
    val location: Location = Location()
): Parcelable {

    @Entity(tableName = "location")
    @Parcelize
    data class Location(
        @PrimaryKey
        val cardId: Long = 0,
        val country: String = "",
        val city: String = ""
    ): Parcelable

    companion object {
        val Empty = Card(images = List(1) { "" }, date = Date().toSimpleDateFormat)
    }
}