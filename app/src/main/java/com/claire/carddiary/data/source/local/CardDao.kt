package com.claire.carddiary.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.claire.carddiary.data.model.Card

@Dao
interface CardDao {

    @Query("SELECT * FROM CARD")
    suspend fun getCards(): List<Card>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: Card)
}