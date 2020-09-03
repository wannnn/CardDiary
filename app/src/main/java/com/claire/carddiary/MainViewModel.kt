package com.claire.carddiary

import androidx.lifecycle.ViewModel
import com.claire.carddiary.utils.SingleLiveEvent

class MainViewModel : ViewModel() {

    private val _saveData = SingleLiveEvent<Any>()
    val saveData: SingleLiveEvent<Any>
        get() = _saveData

    fun callSaveData() {
        _saveData.call()
    }
}