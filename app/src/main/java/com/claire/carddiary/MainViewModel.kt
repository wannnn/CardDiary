package com.claire.carddiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.claire.carddiary.utils.SingleLiveEvent

class MainViewModel : ViewModel() {

    private val _statusBarHeight = MutableLiveData<Int>()
    val statusBarHeight: LiveData<Int>
        get() = _statusBarHeight

    private val _saveData = SingleLiveEvent<Any>()
    val saveData: SingleLiveEvent<Any>
        get() = _saveData

    private val _isExpand = MutableLiveData<Boolean>()
    val isExpand: LiveData<Boolean>
        get() = _isExpand

    private val _fabClick = MutableLiveData<Int>()
    val fabClick: LiveData<Int>
        get() = _fabClick

    init {
        _isExpand.value = false
    }

    fun setStatusBarHeight(height: Int) {
        _statusBarHeight.value = height
    }

    fun callSaveData() {
        _saveData.call()
    }

    fun setExpand() {
        _isExpand.value = _isExpand.value?.not()
    }

    fun fabClick(position: Int) {
        _fabClick.value = position
    }
}