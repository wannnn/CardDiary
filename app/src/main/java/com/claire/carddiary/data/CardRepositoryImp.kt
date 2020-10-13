package com.claire.carddiary.data

import com.claire.carddiary.Resource
import com.claire.carddiary.data.model.Card
import com.claire.carddiary.data.source.CardDataSource

class CardRepositoryImp(
    private val localDataSource: CardDataSource,
    private val remoteDataSource: CardDataSource
): CardRepository {

    override suspend fun getCards(): Resource<List<Card>> {
        return localDataSource.getCards()
    }

    override suspend fun insertCard(card: Card) {
//        remoteDataSource.insertCard(card)

        val localCardData = card.copy(images = listOf(card.images[0]))
        localDataSource.insertCard(localCardData)
    }

}