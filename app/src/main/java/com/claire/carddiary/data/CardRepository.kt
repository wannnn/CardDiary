package com.claire.carddiary.data

import android.net.Uri
import com.claire.carddiary.Resource
import com.claire.carddiary.data.model.Card
import kotlinx.coroutines.flow.Flow

interface CardRepository {

    suspend fun getCards(): Resource<List<Card>>

    suspend fun insertImages(images: List<String>?): Flow<List<Uri?>>

    suspend fun insertCard(card: Card)
}