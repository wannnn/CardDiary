package com.claire.carddiary.data.source.remote

import com.claire.carddiary.Resource
import com.claire.carddiary.data.model.Card
import com.claire.carddiary.data.source.CardDataSource
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.io.InputStream

object CardRemoteDataSource : CardDataSource {

    private val firebase = Firebase.firestore
    private val storage = Firebase.storage
    var storageRef = storage.reference

    override suspend fun getCards(): Resource<List<Card>> {

        return try {

            val result = firebase.collection("cards")
                .get()
                .await()
                .mapNotNull { it.toObject() as? Card }

            Resource.Success(result)

        } catch (e: Exception) {
            Resource.NetworkError("Error getting documents")
        }

    }

    override suspend fun uploadPhoto(pathString: String, inputStream: InputStream): Resource<String> {

        return try {

            val imageRef = storageRef.child(pathString)
            val url = imageRef.putStream(inputStream)
                .await()
                .storage
                .downloadUrl
                .await()
                .toString()

            Resource.Success(url)

        } catch (e: Exception) {
            Resource.NetworkError("Error upload photo")
        }

    }

    override suspend fun insertCard(card: Card): Resource<Boolean> {

        return try {

            firebase.collection("cards")
                .document(card.cardId)
                .set(card)
                .await()

            Resource.Success(true)

        } catch (e: Exception) {
            Resource.NetworkError("Error adding document")
        }

    }

}