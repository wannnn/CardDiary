package com.claire.carddiary.data.source.remote

import android.util.Log
import com.claire.carddiary.Resource
import com.claire.carddiary.data.model.Card
import com.claire.carddiary.data.source.CardDataSource
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object CardRemoteDataSource : CardDataSource {

    private val firebase = Firebase.firestore

    override suspend fun getCards(): Resource<List<Card>> {
        return Resource.Success(listOf())
    }

    override suspend fun insertCard(card: Card) {

    }

    fun addData() {
        // Create a new user with a first and last name
        val card = hashMapOf(
            "cardId" to "Ada",
            "images" to "Lovelace",
            "title" to 1815,
            "date" to 1815,
            "weather" to 1815,
            "content" to 1815,
            "location" to 1815
        )

        // Add a new document with a generated ID
        firebase.collection("cards")
            .add(card)
            .addOnSuccessListener { documentReference ->
                Log.d(javaClass.simpleName, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(javaClass.simpleName, "Error adding document", e)
            }
    }

    fun readData() {
        firebase.collection("cards")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(javaClass.simpleName, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(javaClass.simpleName, "Error getting documents.", exception)
            }
    }

}