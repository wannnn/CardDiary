package com.claire.carddiary.data

import com.claire.carddiary.Resource
import com.claire.carddiary.data.model.Card
import com.claire.carddiary.data.source.CardDataSource
import java.io.InputStream

class CardRepositoryImp(
    private val localDataSource: CardDataSource,
    private val remoteDataSource: CardDataSource
): CardRepository {

    override suspend fun getCards(): Resource<List<Card>> {
        return remoteDataSource.getCards()
    }

    override suspend fun uploadPhoto(pathString: String, inputStream: InputStream): Resource<String> {
        return remoteDataSource.uploadPhoto(pathString, inputStream)
    }

    override suspend fun insertCard(card: Card): Resource<Boolean> {

//        val localCardData = card.copy(images = listOf(card.images[0]))
//        localDataSource.insertCard(localCardData)

        return remoteDataSource.insertCard(card)
    }

}