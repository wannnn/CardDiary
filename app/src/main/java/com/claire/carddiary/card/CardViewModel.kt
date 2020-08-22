package com.claire.carddiary.card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.claire.carddiary.data.Card

class CardViewModel : ViewModel() {

    private val _cardList = MutableLiveData<List<Card>>()
    val cardList: LiveData<List<Card>>
        get() = _cardList

    private val _card = MutableLiveData(Card(images = List(1) { "" }))
    val card: LiveData<Card>
        get() = _card

    private val _alertMsg = MutableLiveData<String>()
    val alertMsg: LiveData<String>
        get() = _alertMsg

    init {
        _cardList.value = List(5) { Card() }
    }

    fun setTitle(title: String) {
        _card.value = _card.value?.copy(title = title)
    }

    fun setImages(images: List<String>) {
        if (_card.value?.images?.get(0).isNullOrEmpty()) {
            _card.value = _card.value?.copy(images = images)
        } else {
            val newList = _card.value?.images?.toMutableList()?.apply { addAll(images) } ?: mutableListOf()
            if (newList.size > 10) {
                _alertMsg.value = "最多只能上傳10張照片"
            } else {
                _card.value = _card.value?.copy(images = newList)
            }
        }
    }
}