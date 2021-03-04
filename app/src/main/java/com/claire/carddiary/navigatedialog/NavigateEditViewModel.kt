package com.claire.carddiary.navigatedialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claire.carddiary.Resource
import com.claire.carddiary.data.CardRepository
import com.claire.carddiary.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class NavigateEditViewModel(
    private val repo: CardRepository
): ViewModel() {

    private val _deleteString = SingleLiveEvent<String>()
    val deleteString: SingleLiveEvent<String> = _deleteString


    fun deleteCard(id: String) = viewModelScope.launch {
        _deleteString.value = when(val result = repo.deleteCard(id)) {
            is Resource.Success -> result.data
            is Resource.NetworkError -> result.errorMessage
            else -> ""
        }
    }

}