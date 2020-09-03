package com.claire.carddiary.card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claire.carddiary.Resource.*
import com.claire.carddiary.data.CardRepository
import com.claire.carddiary.data.model.Card
import com.claire.carddiary.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class CardViewModel(
    private val repository: CardRepository
) : ViewModel() {

    private val _cardList = MutableLiveData<List<Card>>()
    val cardList: LiveData<List<Card>>
        get() = _cardList

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String>
        get() = _errorMsg

    private val _navigateToEdit = SingleLiveEvent<Any>()
    val navigateToEdit: SingleLiveEvent<Any>
        get() = _navigateToEdit


    fun getCards() = viewModelScope.launch {
        when(val resource = repository.getCards()) {
            is Success -> _cardList.value = resource.data
            is Error -> _errorMsg.value = resource.errorMessage
        }
    }

    fun getCard(index: Int): Card {
        return _cardList.value?.getOrNull(index) ?: Card()
    }

    fun navigateToEdit() {
        _navigateToEdit.call()
    }

}