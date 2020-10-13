package com.claire.carddiary.data

import com.claire.carddiary.Resource
import com.claire.carddiary.data.model.Card

interface CardRepository {

    suspend fun getCards(): Resource<List<Card>>

    suspend fun insertCard(card: Card)
}