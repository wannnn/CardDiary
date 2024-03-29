package com.claire.carddiary.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.claire.carddiary.data.model.Card

class EditViewModel : ViewModel() {

    private val _card = MutableLiveData(Card())
    fun getCard(): LiveData<Card> = _card

    private val _maxPhotoMsg = MutableLiveData<Unit>()
    fun getMaxPhotoMsg(): LiveData<Unit> = _maxPhotoMsg

    fun hasImages(position: Int) = _card.value?.images?.getOrNull(position).isNullOrEmpty().not()

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
            }.orEmpty()

            if (newList.size > 10) {
                _maxPhotoMsg.value = Unit
            } else {
                _card.value = _card.value?.copy(images = newList)
            }
        }
    }

    fun deleteImage(position: Int) {

        val newList = _card.value?.images?.toMutableList()?.apply {
            if (_card.value?.images?.size == 1) {
                removeAt(position)
                add("")
            } else {
                removeAt(position)
            }
        }.orEmpty()

        _card.value = _card.value?.copy(images = newList)
    }

    fun setDate(date: Long) {
        _card.value = _card.value?.copy(date = date)
    }

    fun setContent(content: String) {
        _card.value = _card.value?.copy(content = content)
    }

}