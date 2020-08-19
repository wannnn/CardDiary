package com.claire.carddiary.card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.claire.carddiary.data.Card

class CardViewModel : ViewModel() {

    private val _cardList = MutableLiveData<List<Card>>()
    val cardList: LiveData<List<Card>>
        get() = _cardList

    private val _imageList = MutableLiveData<List<String>>()
    val imageList: LiveData<List<String>>
        get() = _imageList

    private val _card = MutableLiveData<Card>()

    init {
        _cardList.value = List(5) { Card() }
        _imageList.value = List(1) {""}
    }

    fun setTitle(title: String) {
        _card.value = _card.value?.copy(title = title)
    }

    fun setImages(images: List<String>) {
        _card.value?.copy(images = images)
        _imageList.value = _imageList.value?.toMutableList()?.apply { addAll(images) }
    }
}