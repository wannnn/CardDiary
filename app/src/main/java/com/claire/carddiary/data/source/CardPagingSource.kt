package com.claire.carddiary.data.source

import androidx.paging.PagingSource
import coil.network.HttpException
import com.claire.carddiary.data.model.Card
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await
import okio.IOException
import java.net.UnknownHostException

class CardPagingSource(
    private val db: FirebaseFirestore
) : PagingSource<QuerySnapshot, Card>() {

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Card> {

        return try {

            val currentPage = params.key ?: db.collection("cards")
                .orderBy("title")
                .limit(5)
                .get()
                .await()

            val lastDocumentSnapshot = runCatching {
                currentPage.documents[currentPage.size() - 1]
            }.getOrNull()

            val nextPage = db.collection("cards")
                .orderBy("title")
                .limit(5)
                .startAfter(lastDocumentSnapshot)
                .get()
                .await()

            LoadResult.Page(
                data = currentPage.toObjects(Card::class.java),
                prevKey = null,
                nextKey = nextPage
            )

        } catch (exception: IOException) {

            println("IOExceptions: ${exception.message}")
            LoadResult.Error(exception)

        } catch (exception: HttpException) {

            println("HttpException: ${exception.message}")
            LoadResult.Error(exception)

        }
    }

}