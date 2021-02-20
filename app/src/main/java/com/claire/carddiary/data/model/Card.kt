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
    @PrimaryKey
    val cardId: String = UUID.randomUUID().toString(),
    val images: List<String> = listOf(),
    val title: String = "",
    val date: String = Date().toSimpleDateFormat,
    val weather: Int = 0,  // ex.(0:sunny 1:rainy)
    val content: String = "",
    val location: Location = Location(),

    // app use
    val isEditView: Boolean = false
): Parcelable {

    @Entity(tableName = "location")
    @Parcelize
    data class Location(
        @PrimaryKey
        val cardId: String = "",
        val country: String = "",
        val city: String = ""
    ): Parcelable
}