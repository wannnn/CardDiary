package com.claire.carddiary.data

import android.net.Uri
import androidx.paging.PagingData
import com.claire.carddiary.Resource
import com.claire.carddiary.data.model.Card
import com.claire.carddiary.data.source.CardDataSource
import kotlinx.coroutines.flow.Flow

class CardRepositoryImp(
    private val localDataSource: CardDataSource,
    private val remoteDataSource: CardDataSource
): CardRepository {

    override suspend fun getCards(): Flow<PagingData<Card>> {
        return remoteDataSource.getCards()
    }

    override suspend fun getKeyWordCards(query: String): Resource<PagingData<Card>> {
        return remoteDataSource.getKeyWordCards(query)
    }

    override suspend fun uploadPhoto(uri: Uri): Resource<String> {
        return remoteDataSource.uploadPhoto(uri)
    }

    override suspend fun insertCard(card: Card): Resource<Boolean> {

//        val localCardData = card.copy(images = listOf(card.images[0]))
//        localDataSource.insertCard(localCardData)

        return remoteDataSource.insertCard(card)
    }

}