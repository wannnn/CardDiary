package com.claire.carddiary.data.source.local

import androidx.room.TypeConverter
import com.claire.carddiary.data.model.Card
import com.claire.carddiary.data.model.Card.Location
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class CardTypeConverters {

    private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @TypeConverter
    fun convertListToJson(list: List<String>?): String? {
        list?.let {
            return moshi.adapter<List<String>>(List::class.java).toJson(list)
        }
        return null
    }

    @TypeConverter
    fun convertJsonToList(json: String?): List<String>? {
        json?.let {
            val type = Types.newParameterizedType(List::class.java, String::class.java)
            val adapter: JsonAdapter<List<String>> = moshi.adapter(type)
            return adapter.fromJson(it)
        }
        return null
    }

    @TypeConverter
    fun convertLocationToJson(location: Location?): String? {
        location?.let {
            return moshi.adapter(Location::class.java).toJson(location)
        }
        return null
    }

    @TypeConverter
    fun convertJsonToLocation(json: String?): Location? {
        json?.let {
            val type = Types.newParameterizedTypeWithOwner(Card::class.java, Location::class.java)
            val adapter: JsonAdapter<Location> = moshi.adapter(type)
            return adapter.fromJson(it)
        }
        return null
    }

    @TypeConverter
    fun convertCardsToJson(cards: List<Card>?): String? {
        cards?.let {
            return moshi.adapter<List<Card>>(List::class.java).toJson(cards)
        }
        return null
    }

    @TypeConverter
    fun convertJsonToCards(json: String?): List<Card>? {
        json?.let {
            val type = Types.newParameterizedType(List::class.java, Card::class.java)
            val adapter: JsonAdapter<List<Card>> = moshi.adapter(type)
            return adapter.fromJson(it)
        }
        return null
    }
}
