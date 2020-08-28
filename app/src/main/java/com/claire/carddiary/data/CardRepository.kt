package com.claire.carddiary.data

interface CardRepository {
    suspend fun getCards()

    suspend fun insertCard()
}