package com.claire.carddiary.card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.claire.carddiary.data.Card

class CardViewModel : ViewModel() {

    private val _cardList = MutableLiveData<List<Card>?>()
    val cardList: LiveData<List<Card>?>
        get() = _cardList

    private val _click = MutableLiveData<String>()
    val click: LiveData<String>
        get() = _click

    init {
        _cardList.value = List(5) { Card() }
    }

    fun click() {
        _click.value = "click!!!"
    }

}