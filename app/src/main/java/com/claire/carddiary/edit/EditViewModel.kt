package com.claire.carddiary.edit

import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claire.carddiary.data.CardRepository
import com.claire.carddiary.data.model.Card
import com.claire.carddiary.data.source.remote.CardRemoteDataSource
import com.claire.carddiary.utils.toSimpleDateFormat
import kotlinx.coroutines.launch
import java.io.InputStream
import java.util.*

class EditViewModel(
    private val repository: CardRepository
) : ViewModel() {

    private val _card = MutableLiveData(Card.Empty)
    val card: LiveData<Card> = _card

    private val _alertMsg = MutableLiveData<String>()
    val alertMsg: LiveData<String> = _alertMsg

    private val _uploadedImages = MutableLiveData<List<String>>(listOf())
    val uploadedImages: LiveData<List<String>> = _uploadedImages

    private val _uploadImgFailMsg = MutableLiveData<String>()
    val uploadImgFailMsg: LiveData<String> = _uploadImgFailMsg

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


    fun getImages(): List<String> = _card.value?.images.orEmpty()

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

    fun insertImages(inputStreams: List<InputStream?>) {

        val pathString = getImages().map { it.toUri().lastPathSegment ?: Date().toSimpleDateFormat }

        inputStreams.forEachIndexed { index, value ->

            viewModelScope.launch {
                val imageRef = CardRemoteDataSource.storageRef.child(pathString[index])
                val uploadTask = value?.let { imageRef.putStream(it) }

                uploadTask?.continueWithTask { task ->

                    if (!task.isSuccessful) {
                        _uploadImgFailMsg.value = "Failure! ${task.result} ${task.exception?.message}"
                    }
                    imageRef.downloadUrl

                }?.addOnCompleteListener { task ->

                    if (!task.isSuccessful) {
                        _uploadImgFailMsg.value = "Failure! ${task.result} ${task.exception?.message}"
                    } else {
                        _uploadedImages.value = _uploadedImages.value?.toMutableList()?.apply {
                            add((task.result ?: Uri.EMPTY).toString())
                        }
                        println(task.result)
                    }

                }
            }

        }

    }

    fun insertCard() = viewModelScope.launch {
        _card.value = _card.value?.copy(images = _uploadedImages.value?.toList().orEmpty())
        repository.insertCard(_card.value ?: Card.Empty)
    }

}