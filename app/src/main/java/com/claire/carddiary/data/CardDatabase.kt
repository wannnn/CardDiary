package com.claire.carddiary.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.claire.carddiary.data.model.Card
import com.claire.carddiary.data.model.User

@Database(entities = [Card::class, User::class], version = 1, exportSchema = false)
abstract class CardDatabase: RoomDatabase() {

    abstract fun cardDao(): CardDao

    companion object {

        private const val DATABASE_NAME = "card_db"

        // For Singleton instantiation
        @Volatile private var instance: CardDatabase? = null

        fun getInstance(context: Context): CardDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): CardDatabase {
            return Room.databaseBuilder(context, CardDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {

                })
                .build()
        }
    }
}