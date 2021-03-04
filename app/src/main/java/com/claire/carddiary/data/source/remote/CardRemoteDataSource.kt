package com.claire.carddiary.data.source.remote

import android.net.Uri
import androidx.paging.*
import com.claire.carddiary.Resource
import com.claire.carddiary.data.model.Card
import com.claire.carddiary.data.source.CardDataSource
import com.claire.carddiary.data.source.CardPagingSource
import com.claire.carddiary.data.source.QueryPagingSource
import com.claire.carddiary.utils.toSimpleDateFormat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import java.util.*

object CardRemoteDataSource : CardDataSource {

    private val firebase = Firebase.firestore
    private val storage = Firebase.storage
    var storageRef = storage.reference

    override fun getCards(query: String): Flow<PagingData<Card>> {

        return if (query.isBlank()) {
            Pager(PagingConfig(pageSize = 5)) {
                CardPagingSource(firebase)
            }.flow
        } else {
            Pager(PagingConfig(pageSize = 5)) {
                QueryPagingSource(firebase, query)
            }.flow
        }

    }

    override suspend fun deleteCard(id: String): Resource<String> {

        return try {

            firebase.collection("cards")
                .document(id)
                .delete()
                .await()

            Resource.Success("刪除成功！")

        } catch (e: Exception) {
            Resource.NetworkError("Error Delete Diary")
        }
    }

    override suspend fun uploadPhoto(uri: Uri): Resource<String> {

        return try {

            val pathString = uri.lastPathSegment ?: Date().toSimpleDateFormat
            val imageRef = storageRef.child(pathString)
            val url = imageRef.putFile(uri)
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