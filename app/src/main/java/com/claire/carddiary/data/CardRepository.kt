package com.claire.carddiary.data

import android.net.Uri
import androidx.paging.PagingData
import com.claire.carddiary.Resource
import com.claire.carddiary.data.model.Card
import kotlinx.coroutines.flow.Flow

interface CardRepository {

    fun getCards(query: String): Flow<PagingData<Card>>

    suspend fun uploadPhoto(uri: Uri): Resource<String>

    suspend fun insertCard(card: Card): Resource<Boolean>
}