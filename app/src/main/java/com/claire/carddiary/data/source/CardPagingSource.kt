package com.claire.carddiary.data.source

import androidx.paging.PagingSource
import com.claire.carddiary.data.model.Card
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class CardPagingSource(
    private val db: FirebaseFirestore
) : PagingSource<QuerySnapshot, Card>() {

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Card> {

        return try {

            val currentPage = params.key ?: db.collection("cards")
                .limit(5)
                .get()
                .await()

            val lastDocumentSnapshot = currentPage.documents[currentPage.size() - 1]

            val nextPage = db.collection("cards")
                .limit(5)
                .startAfter(lastDocumentSnapshot)
                .get()
                .await()

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