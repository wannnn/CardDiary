package com.claire.carddiary.data.source

import android.net.Uri
import com.claire.carddiary.Resource
import com.claire.carddiary.data.model.Card
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.flow.Flow

interface CardDataSource {

    suspend fun getCards(): Resource<List<Card>>

    suspend fun insertImages(images: List<String>?): Flow<List<Uri?>>

    suspend fun insertCard(card: Card)
}