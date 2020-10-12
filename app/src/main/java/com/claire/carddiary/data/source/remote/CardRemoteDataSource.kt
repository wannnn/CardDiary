package com.claire.carddiary.data.source.remote

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.claire.carddiary.CardApplication.Companion.instance
import com.claire.carddiary.Resource
import com.claire.carddiary.data.model.Card
import com.claire.carddiary.data.source.CardDataSource
import com.claire.carddiary.utils.toSimpleDateFormat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.*
import java.util.*

object CardRemoteDataSource : CardDataSource {

    private val firebase = Firebase.firestore
    private val storage = Firebase.storage
    private var storageRef = storage.reference

    override suspend fun getCards(): Resource<List<Card>> {
        return Resource.Success(listOf())
    }

    override suspend fun insertImages(images: List<String>?) = flow {

        val pathString = images?.map { it.toUri().lastPathSegment ?: Date().toSimpleDateFormat }.orEmpty()
        val imagesUris: MutableList<Uri?> = mutableListOf()

        images?.forEachIndexed { index, value ->
            val imageRef = storageRef.child(pathString[index])
            val uploadTask = instance.contentResolver?.openInputStream(value.toUri())?.let {
                imageRef.putStream(it)
            }
            uploadTask?.continueWithTask { task ->
                if (!task.isSuccessful) {
                    println("Failure! ${task.result} ${task.exception?.message}")
                }
                imageRef.downloadUrl
            }?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    imagesUris.add(task.result)
                } else {
                    println("Failure! ${task.result} ${task.exception?.message}")
                }
            }

            if (index == images.size) emit(imagesUris)
        }
    }

    override suspend fun insertCard(card: Card) {

        val data = hashMapOf(
            "cardId" to "Ada",
            "images" to "Lovelace",
            "title" to 1815,
            "date" to 1815,
            "weather" to 1815,
            "content" to 1815,
            "location" to 1815
        )

        val reference = firebase.collection("cards").add(card)
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
        val snapshot = firebase.collection("cards").get()


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