package com.claire.carddiary.utils

import androidx.annotation.VisibleForTesting
import com.claire.carddiary.data.CardRepository
import com.claire.carddiary.data.CardRepositoryImp
import com.claire.carddiary.data.source.local.CardLocalDataSource
import com.claire.carddiary.data.source.remote.CardRemoteDataSource

object ServiceLocator {

    @Volatile
    var cardRepository: CardRepository? = null
        @VisibleForTesting set

    fun provideRepository(): CardRepository {
        synchronized(this) {
            return cardRepository
                ?: cardRepository
                ?: createRepository()
        }
    }

    private fun createRepository(): CardRepository {
        return CardRepositoryImp(
            CardLocalDataSource(),
            CardRemoteDataSource
        )
    }

}