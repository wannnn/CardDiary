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
    val cardList: LiveData<List<Card>> = _cardList

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> = _errorMsg

    private val _navigateToEdit = SingleLiveEvent<Any>()
    val navigateToEdit: SingleLiveEvent<Any> = _navigateToEdit

    private val _isExpand = MutableLiveData<Boolean>(false)
    val isExpand: LiveData<Boolean> = _isExpand

    private val _fabClick = SingleLiveEvent<Int>()
    val fabClick: SingleLiveEvent<Int> = _fabClick



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

    fun setExpand() {
        _isExpand.value = _isExpand.value?.not()
    }

    fun fabClick(position: Int) {
        _fabClick.value = position
    }

}