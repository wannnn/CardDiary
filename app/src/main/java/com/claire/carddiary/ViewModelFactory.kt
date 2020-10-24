package com.claire.carddiary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.claire.carddiary.card.CardViewModel
import com.claire.carddiary.utils.ServiceLocator

@Suppress("UNCHECKED_CAST")
class ViewModelFactory : ViewModelProvider.Factory {

    private val cardRepository = ServiceLocator.provideRepository()

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(cardRepository)
                isAssignableFrom(CardViewModel::class.java) ->
                    CardViewModel(cardRepository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}