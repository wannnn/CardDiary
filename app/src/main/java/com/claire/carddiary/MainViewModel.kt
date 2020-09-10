package com.claire.carddiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.claire.carddiary.utils.SingleLiveEvent

class MainViewModel : ViewModel() {

    private val _saveData = SingleLiveEvent<Any>()
    val saveData: SingleLiveEvent<Any>
        get() = _saveData

    private val _isExpand = MutableLiveData<Boolean>()
    val isExpand: LiveData<Boolean>
        get() = _isExpand

    init {
        _isExpand.value = false
    }

    fun callSaveData() {
        _saveData.call()
    }

    fun setExpand() {
        _isExpand.value = _isExpand.value?.not()
    }
}