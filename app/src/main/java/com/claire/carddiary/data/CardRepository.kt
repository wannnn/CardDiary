package com.claire.carddiary.data

import com.claire.carddiary.Resource
import com.claire.carddiary.data.model.Card
import java.io.InputStream

interface CardRepository {

    suspend fun getCards(): Resource<List<Card>>

    suspend fun uploadPhoto(pathString: String, inputStream: InputStream): Resource<String>

    suspend fun insertCard(card: Card): Resource<Boolean>
}