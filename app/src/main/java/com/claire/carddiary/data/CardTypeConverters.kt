package com.claire.carddiary.data

import androidx.room.TypeConverter
import com.claire.carddiary.data.model.Card
import com.claire.carddiary.data.model.Card.Location
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class CardTypeConverters {

    @TypeConverter
    fun convertListToJson(list: List<String>?): String? {
        list?.let {
            return Moshi.Builder().build().adapter<List<String>>(List::class.java).toJson(list)
        }
        return null
    }

    @TypeConverter
    fun convertJsonToList(json: String?): List<String>? {
        json?.let {
            val type = Types.newParameterizedType(List::class.java, String::class.java)
            val adapter: JsonAdapter<List<String>> = Moshi.Builder().build().adapter(type)
            return adapter.fromJson(it)
        }
        return null
    }

    @TypeConverter
    fun convertLocationToJson(location: Location?): String? {
        location?.let {
            return Moshi.Builder().build().adapter(Location::class.java).toJson(location)
        }
        return null
    }

    @TypeConverter
    fun convertJsonToLocation(json: String?): Location? {
        json?.let {
            val type = Types.newParameterizedType(Location::class.java)
            val adapter: JsonAdapter<Location> = Moshi.Builder().build().adapter(type)
            return adapter.fromJson(it)
        }
        return null
    }

    @TypeConverter
    fun convertCardsToJson(cards: List<Card>?): String? {
        cards?.let {
            return Moshi.Builder().build().adapter<List<Card>>(List::class.java).toJson(cards)
        }
        return null
    }

    @TypeConverter
    fun convertJsonToCards(json: String?): List<Card>? {
        json?.let {
            val type = Types.newParameterizedType(List::class.java, Card::class.java)
            val adapter: JsonAdapter<List<Card>> = Moshi.Builder().build().adapter(type)
            return adapter.fromJson(it)
        }
        return null
    }
}
