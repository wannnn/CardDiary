package com.claire.carddiary

import android.net.Uri
import android.util.Log
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

class MainViewModel(
    private val repository: CardRepository
) : ViewModel() {

    private var _card = Card.Empty

    private val _refresh = MutableLiveData<Boolean>()
    val refresh: LiveData<Boolean> = _refresh

    private val _uploadedImages = MutableLiveData<List<String>>(listOf())
    val uploadedImages: LiveData<List<String>> = _uploadedImages

    private val _uploadImgFailMsg = MutableLiveData<String>()
    val uploadImgFailMsg: LiveData<String> = _uploadImgFailMsg


    fun getImagesSize() = _card.images.size

    fun insertImages(card: Card, inputStreams: List<InputStream?>) {

        _card = card

        val pathString = card.images.map { it.toUri().lastPathSegment ?: Date().toSimpleDateFormat }

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
        _card = _card.copy(images = _uploadedImages.value?.toList().orEmpty())

        CardRemoteDataSource.firebase
            .collection("cards")
            .document(_card.cardId)
            .set(_card)
            .continueWith { task ->
                if (task.isSuccessful) {
                    _refresh.value = true
                } else {
                    println("Error adding document")
                }
            }

    }
}