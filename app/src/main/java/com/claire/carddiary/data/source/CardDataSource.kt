package com.claire.carddiary.data.source

import com.claire.carddiary.Resource
import com.claire.carddiary.data.model.Card

interface CardDataSource {

    suspend fun getCards(): Resource<List<Card>>

    suspend fun insertCard(card: Card)
}