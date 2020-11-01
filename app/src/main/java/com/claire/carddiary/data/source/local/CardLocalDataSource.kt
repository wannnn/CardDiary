package com.claire.carddiary.data.source.local

import com.claire.carddiary.CardApplication
import com.claire.carddiary.Resource
import com.claire.carddiary.data.model.Card
import com.claire.carddiary.data.source.CardDataSource
import java.io.InputStream

class CardLocalDataSource : CardDataSource {

    private val db = CardDatabase.getInstance(CardApplication.instance).cardDao()

    override suspend fun getCards(): Resource<List<Card>> {
        return try {
            Resource.Success(db.getCards())
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage.orEmpty())
        }
    }

    override suspend fun uploadPhoto(pathString: String, inputStream: InputStream): Resource<String> {
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