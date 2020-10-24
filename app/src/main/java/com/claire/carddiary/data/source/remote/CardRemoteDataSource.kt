package com.claire.carddiary.data.source.remote

import com.claire.carddiary.Resource
import com.claire.carddiary.data.model.Card
import com.claire.carddiary.data.source.CardDataSource
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

object CardRemoteDataSource : CardDataSource {

    val firebase = Firebase.firestore
    val storage = Firebase.storage
    var storageRef = storage.reference

    override suspend fun getCards(): Resource<List<Card>> {
        return Resource.Success(listOf())
    }

    override suspend fun insertCard(card: Card) {

    }

}