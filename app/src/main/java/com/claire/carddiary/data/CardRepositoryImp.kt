package com.claire.carddiary.data

import com.claire.carddiary.CardApplication
import com.claire.carddiary.Resource
import com.claire.carddiary.Resource.*
import com.claire.carddiary.data.model.Card
import java.lang.Exception

class CardRepositoryImp: CardRepository {

    private val db = CardDatabase.getInstance(CardApplication.instance).cardDao()


    override suspend fun getCards(): Resource<List<Card>> {
        return try {
            Success(db.getCards())
        } catch (e: Exception) {
            Error(e.localizedMessage.orEmpty())
        }
    }

    override suspend fun insertCard(card: Card) {
        db.insertCard(card)
    }

}