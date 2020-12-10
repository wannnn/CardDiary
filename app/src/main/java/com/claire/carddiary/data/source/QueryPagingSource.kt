package com.claire.carddiary.data.source

import androidx.paging.PagingSource
import com.claire.carddiary.data.model.Card
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await

class QueryPagingSource(
    private val db: FirebaseFirestore,
    private val query: String
) : PagingSource<QuerySnapshot, Card>() {

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Card> {

        return try {

            val currentPage = params.key ?: db.collection("cards")
                .whereEqualTo("title", query)
                .limit(5)
                .get(Source.SERVER)
                .await()

            val nextPage = when {
                currentPage.metadata.isFromCache -> throw Exception("No Internet Connect!")
                currentPage.size() == 0 -> null
                else -> {
                    val lastDocumentSnapshot = currentPage.documents[currentPage.size() - 1]
                    db.collection("cards")
                        .whereEqualTo("title", query)
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

}