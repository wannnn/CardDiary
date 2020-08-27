package com.claire.carddiary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.claire.carddiary.data.CardRepositoryImp
import com.claire.carddiary.edit.CardViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(CardViewModel::class.java) ->
                    CardViewModel(CardRepositoryImp())
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}