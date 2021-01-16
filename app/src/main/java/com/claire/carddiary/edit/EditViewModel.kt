package com.claire.carddiary.edit

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.claire.carddiary.data.model.Card
import com.claire.carddiary.utils.toSimpleDateFormat
import java.util.*

class EditViewModel : ViewModel() {

    private val _card = MutableLiveData(Card())
    val card: LiveData<Card> = _card

    private val _alertMsg = MutableLiveData<String>()
    val alertMsg: LiveData<String> = _alertMsg

    val titleTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            setTitle(s.toString())
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    val contentTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            setContent(s.toString())
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    init {
        _card.value = _card.value?.copy(images = listOf(""))
    }

    fun getCard(): Card = _card.value ?: Card()

    fun setScriptCard(card: Card?) {  // 草搞
        card?.let { _card.value = card }
    }

    fun setTitle(title: String) {
        _card.value = _card.value?.copy(title = title)
    }

    fun setImages(images: List<String>) {
        if (_card.value?.images?.get(0).isNullOrEmpty()) {
            _card.value = _card.value?.copy(images = images)
        } else {
            val newList = _card.value?.images?.toMutableList()?.apply {
                addAll(images)
            } ?: mutableListOf()
            if (newList.size > 10) {
                _alertMsg.value = "最多只能上傳10張照片"
            } else {
                _card.value = _card.value?.copy(images = newList)
            }
        }
    }

    fun setDate(date: Date) {
        _card.value = _card.value?.copy(date = date.toSimpleDateFormat)
    }

    fun setContent(content: String) {
        _card.value = _card.value?.copy(content = content)
    }

}