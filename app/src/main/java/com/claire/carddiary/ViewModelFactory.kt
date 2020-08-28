package com.claire.carddiary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.claire.carddiary.data.CardRepositoryImp
import com.claire.carddiary.card.CardViewModel
import com.claire.carddiary.data.CardDatabase
import com.claire.carddiary.edit.EditViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory : ViewModelProvider.Factory {

    val db = CardDatabase.getInstance(CardApplication.instance).cardDao()

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(CardViewModel::class.java) ->
                    CardViewModel(CardRepositoryImp())
                isAssignableFrom(EditViewModel::class.java) ->
                    EditViewModel(CardRepositoryImp())
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}