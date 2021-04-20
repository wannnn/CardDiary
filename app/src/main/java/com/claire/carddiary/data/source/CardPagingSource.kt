package com.claire.carddiary.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.claire.carddiary.data.model.Card
import com.claire.carddiary.login.AuthManager
import com.google.firebase.firestore.Query.Direction.*
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class CardPagingSource : PagingSource<QuerySnapshot, Card>() {

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Card> {

        return try {

            val currentPage = params.key ?: Firebase.firestore.collection(AuthManager.userId)
                .orderBy("date", DESCENDING)
                .limit(5)
                .get(Source.SERVER)
                .await()

            val nextPage = when {
                currentPage.metadata.isFromCache -> throw Exception("No Internet Connect!")
                currentPage.size() == 0 -> null
                else -> {
                    val lastDocumentSnapshot = currentPage.documents[currentPage.size() - 1]
                    Firebase.firestore.collection(AuthManager.userId)
                        .orderBy("date", DESCENDING)
                        .limit(5)
                        .startAfter(lastDocumentSnapshot)
                        .get(Source.SERVER)
                        .await()
                }
            }

            LoadResult.Page(
                data = currentPage.toObjects(Card::class.java),
                prevKey = null,
                nextKey = nextPage
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<QuerySnapshot, Card>): QuerySnapshot? {
        return null
    }

}