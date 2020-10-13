package com.claire.carddiary.edit

import android.text.Editable
import android.text.TextWatcher
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claire.carddiary.CardApplication
import com.claire.carddiary.data.CardRepository
import com.claire.carddiary.data.model.Card
import com.claire.carddiary.data.source.remote.CardRemoteDataSource
import com.claire.carddiary.utils.toSimpleDateFormat
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class EditViewModel(
    private val repository: CardRepository
) : ViewModel() {

    private val _card = MutableLiveData(Card.Empty)
    val card: LiveData<Card>
        get() = _card

    private val _alertMsg = MutableLiveData<String>()
    val alertMsg: LiveData<String>
        get() = _alertMsg

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


    fun setInitCard(card: Card?) {
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

    fun saveData() = viewModelScope.launch {
//        CardRemoteDataSource.firebase
        insertImages()
        repository.insertCard(_card.value ?: Card.Empty)
    }

    private fun insertImages() = viewModelScope.launch {
        val pathString = _card.value?.images?.map { it.toUri().lastPathSegment ?: Date().toSimpleDateFormat }.orEmpty()

        _card.value?.images?.forEachIndexed { index, value ->
            val imageRef = CardRemoteDataSource.storageRef.child(pathString[index])
            val uploadTask = CardApplication.instance.contentResolver?.openInputStream(value.toUri())?.let {
                imageRef.putStream(it)
            }
            uploadTask?.continueWithTask { task ->
                if (!task.isSuccessful) {
                    println("Failure! ${task.result} ${task.exception?.message}")
                }
                imageRef.downloadUrl
            }?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    println(task.result)
                } else {
                    println("Failure! ${task.result} ${task.exception?.message}")
                }
            }
        }
    }
}