package com.claire.carddiary.data

import android.net.Uri
import androidx.paging.PagingData
import com.claire.carddiary.Resource
import com.claire.carddiary.data.model.Card
import com.claire.carddiary.data.source.CardDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class CardRepositoryImp(
    private val localDataSource: CardDataSource,
    private val remoteDataSource: CardDataSource
): CardRepository {

    override fun getCards(query: String): Flow<PagingData<Card>> {
        return remoteDataSource.getCards(query)
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