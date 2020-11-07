package com.claire.carddiary.data.source

import android.net.Uri
import com.claire.carddiary.Resource
import com.claire.carddiary.data.model.Card

interface CardDataSource {

    suspend fun getCards(): Resource<List<Card>>

    suspend fun uploadPhoto(uri: Uri): Resource<String>

    suspend fun insertCard(card: Card): Resource<Boolean>
}