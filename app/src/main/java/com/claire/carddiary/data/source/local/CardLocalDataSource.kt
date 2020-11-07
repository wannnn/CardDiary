package com.claire.carddiary.data.source.local

import android.net.Uri
import androidx.paging.PagingData
import com.claire.carddiary.CardApplication
import com.claire.carddiary.Resource
import com.claire.carddiary.data.model.Card
import com.claire.carddiary.data.source.CardDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class CardLocalDataSource : CardDataSource {

    private val db = CardDatabase.getInstance(CardApplication.instance).cardDao()

    override suspend fun getCards(): Flow<PagingData<Card>> {
        return emptyFlow()
    }

    override suspend fun uploadPhoto(uri: Uri): Resource<String> {
        return Resource.Success("")
    }

    override suspend fun insertCard(card: Card): Resource<Boolean> {
        return try {
            db.insertCard(card)
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage.orEmpty())
        }
    }

}